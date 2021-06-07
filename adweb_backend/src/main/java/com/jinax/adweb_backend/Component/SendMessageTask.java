package com.jinax.adweb_backend.Component;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * @author jinaxCai
 */
class SendMessageTask implements Callable<Boolean> {
    private final WebSocketSession user;
    private final TextMessage returnMessage;

    public SendMessageTask(WebSocketSession user, TextMessage returnMessage) {
        this.user = user;
        this.returnMessage = returnMessage;
    }

    @Override
    public Boolean call() throws Exception {
        if (user != null && user.isOpen()) {
            for (int i = 0; i < 5; i++) {
                try {
                    user.sendMessage(returnMessage);
                    return true;
                } catch (IOException e) {
                    //do nothing
                }
            }
        }
        return false;
    }
}
