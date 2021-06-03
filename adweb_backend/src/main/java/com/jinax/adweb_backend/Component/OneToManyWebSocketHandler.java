package com.jinax.adweb_backend.Component;
import com.jinax.adweb_backend.Entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;

public class OneToManyWebSocketHandler implements WebSocketHandler {

    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        users.add(session);
        System.out.println("ConnectionEstablished"+"=>当前在线用户的数量是:"+users.size());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if(SecurityContextHolder.getContext().getAuthentication() == null) {
            session.close(CloseStatus.POLICY_VIOLATION);
            users.remove(session);
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
        users.remove(session);
    }


    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

        users.remove(session);
        System.out.println("ConnectionClosed"+"=>当前在线用户的数量是:"+users.size());

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