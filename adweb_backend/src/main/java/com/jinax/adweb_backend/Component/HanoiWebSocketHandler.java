package com.jinax.adweb_backend.Component;

import com.jinax.adweb_backend.Component.Tower.Hanoi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;

/**
 * @author : chara
 */
public class HanoiWebSocketHandler implements WebSocketHandler {

    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();;
    private static final Logger LOGGER = LoggerFactory.getLogger(HanoiWebSocketHandler.class);
    private final Hanoi hanoi;

    public HanoiWebSocketHandler(Hanoi hanoi) {
        this.hanoi = hanoi;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        synchronized (this){
            users.add(session);
        }
        LOGGER.info("ConnectionEstablished"+"=>当前在线用户的数量是:{}",users.size());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        
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
}
