package com.jinax.adweb_backend.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;


import java.util.*;

import java.util.concurrent.*;

public class ChatServer implements WebSocketHandler {
    private static final Map<String,WebSocketSession> users = new ConcurrentHashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatServer.class);
    private static final ExecutorService pool = Executors.newFixedThreadPool(5);
    private static final Timer timer = new Timer();

    public ChatServer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                List<String> userList = new ArrayList<>(users.keySet());
                Map<String,List<String>> userMap = new HashMap<>();
                userMap.put("users",userList);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    String userListString = mapper.writeValueAsString(userMap);
                    users.values().forEach((user)->{
                        pool.submit(new SendMessageTask(user,new TextMessage(userListString)));
                    });
                } catch (JsonProcessingException e) {
                    LOGGER.error("json failed {}",e.getMessage());
                }

            }
        },1000,5000);

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        users.put((String) session.getAttributes().get("username"),session);
        LOGGER.info("ConnectionEstablished"+"=>当前在线用户的数量是:{}",users.size());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String username = (String) session.getAttributes().get("username");
        LOGGER.info("Data from {}"+"=>{}",username,message.getPayload());
        ObjectMapper mapper = new ObjectMapper();
        InboundData inboundData = mapper.readValue((String) message.getPayload(),InboundData.class);
        if(inboundData.getTo() == null){
            sendMessageToUsers(session,inboundData);
        }else{
            sendMessage(session,inboundData);
        }

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        users.remove((String) session.getAttributes().get("username"));
        LOGGER.info("ConnectionClosed"+"=>当前在线用户的数量是:{}",users.size());

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给特定用户发送
     * @param session session
     * @param inboundData 收到的信息
     * @throws JsonProcessingException json exception, should not happen
     */
    private void sendMessage(WebSocketSession session,InboundData inboundData) throws JsonProcessingException {
        List<String> toUsers = inboundData.getTo();
        //同时也要发给自己
        TextMessage returnMessage = getTextMessage(session, inboundData);
        for(String user:toUsers){
            pool.submit(new SendMessageTask(users.get(user), returnMessage));// omit the future returned
        }
        pool.submit(new SendMessageTask(session, returnMessage));// 也给自己发一份，很恶心，请问lyq

    }

    /**
     * 给所有在线用户发送消息
     * @param inboundData 收到的信息
     */
    private void sendMessageToUsers(WebSocketSession session,InboundData inboundData) throws JsonProcessingException {
        //这里可能因为并发问题导致访问到已经退出的 user，但是不关键
        TextMessage returnMessage = getTextMessage(session, inboundData);
        for (WebSocketSession user : users.values()) {
            pool.submit(new SendMessageTask(user,returnMessage));// omit the future returned
        }
    }

    private TextMessage getTextMessage(WebSocketSession session, InboundData inboundData) throws JsonProcessingException {
        String username = (String) session.getAttributes().get("username");
        OutBoundData outBoundData = new OutBoundData(username, inboundData.at, inboundData.message, inboundData.to);
        ObjectMapper mapper = new ObjectMapper();
        String message = mapper.writeValueAsString(outBoundData);
        return new TextMessage(message);
    }

    private static class InboundData{
        private String message;
        private List<String> at;
        private List<String> to;

        public InboundData() {
        }

        public InboundData(String message, List<String> at, List<String> to) {
            this.message = message;
            this.at = at;
            this.to = to;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<String> getAt() {
            return at;
        }

        public void setAt(List<String> at) {
            this.at = at;
        }

        public List<String> getTo() {
            return to;
        }

        public void setTo(List<String> to) {
            this.to = to;
        }
    }

    private static class OutBoundData{
        private String author;
        private List<String> at;
        private String message;
        private List<String> to;


        public OutBoundData() {
        }

        public OutBoundData(String author, List<String> at, String message, List<String> to) {
            this.author = author;
            this.at = at;
            this.message = message;
            this.to = to;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public List<String> getAt() {
            return at;
        }

        public void setAt(List<String> at) {
            this.at = at;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<String> getTo() {
            return to;
        }

        public void setTo(List<String> to) {
            this.to = to;
        }
    }
}