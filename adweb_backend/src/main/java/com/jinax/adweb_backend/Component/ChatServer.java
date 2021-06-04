package com.jinax.adweb_backend.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ChatServer implements WebSocketHandler {
    private static final HashMap<String,WebSocketSession> users = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatServer.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        users.put((String) session.getAttributes().get("username"),session);
        LOGGER.info("ConnectionEstablished"+"=>当前在线用户的数量是:{}",users.size());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String username = (String) session.getAttributes().get("username");
        LOGGER.info("Data from {}"+"=>{}",username,message.getPayload());
        InboundData inboundData = JSON.parseObject((String) message.getPayload(), InboundData.class, JsonFactory.Feature.collectDefaults());
        TextMessage returnMessage = new TextMessage(username + " : " + inboundData.getMessage());
        sendMessageToUsers(returnMessage);
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
     * 给所有在线用户发送消息
     * @param message message
     */
    public void sendMessageToUsers(TextMessage message) {
        //这里可能因为并发问题导致访问到已经退出的 user，但是不关键
        for (WebSocketSession user : users.values()) {
            if (user.isOpen()) {
                for (int i = 0; i < 5; i++) {
                    try {
                        user.sendMessage(message);
                        break;
                    }catch(IOException e){
                        //do nothing
                    }
                }
            }
        }
    }

    private class InboundData{
        @JSONField(name="message")
        private String message;
        @JSONField(name = "at")
        private List<String> at;

        public InboundData(String message, List<String> at) {
            this.message = message;
            this.at = at;
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
    }
}