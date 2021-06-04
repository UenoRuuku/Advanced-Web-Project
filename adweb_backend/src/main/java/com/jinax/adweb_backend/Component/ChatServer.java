package com.jinax.adweb_backend.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;

public class ChatServer implements WebSocketHandler {

    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();;
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatServer.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        synchronized (this){
            users.add(session);
        }
        LOGGER.info("ConnectionEstablished"+"=>当前在线用户的数量是:{}",users.size());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if(SecurityContextHolder.getContext().getAuthentication() == null) {
            session.close(CloseStatus.POLICY_VIOLATION);
            synchronized (this){
                users.remove(session);
            }
        }
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TextMessage returnMessage = new TextMessage(username + " : " + message.getPayload());

        sendMessageToUsers(returnMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        synchronized (this){
            users.remove(session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        synchronized (this){
            users.remove(session);
        }
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
        for (WebSocketSession user : users) {
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

}