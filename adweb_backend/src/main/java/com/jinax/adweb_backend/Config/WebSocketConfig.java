package com.jinax.adweb_backend.Config;

import com.jinax.adweb_backend.Component.HanoiWebSocketHandler;
import com.jinax.adweb_backend.Component.ChatServer;
import com.jinax.adweb_backend.Component.Tower.Hanoi;
import com.jinax.adweb_backend.Service.AggregateService;
import com.jinax.adweb_backend.Service.GameHistoryService;
import com.jinax.adweb_backend.Service.HanoiHistoryService;
import com.jinax.adweb_backend.Service.OperationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Collections;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Value("${hanoi.numTowers}")
    private int numTowers;

    private final AggregateService aggregateService;
    private final GameHistoryService gameHistoryService;

    public WebSocketConfig(AggregateService aggregateService, GameHistoryService gameHistoryService) {
        this.aggregateService = aggregateService;
        this.gameHistoryService = gameHistoryService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler(), "/chat/*").
                setAllowedOrigins("*").
                addInterceptors(new HttpSessionHandshakeInterceptor(Collections.singleton("username")));
        registry.addHandler(hanoiHandler(),"/hanoi/*").setAllowedOrigins("*");
    }

    @Bean
    public Hanoi hanoi(){
        return new Hanoi(numTowers);
    }

    @Bean
    public WebSocketHandler chatHandler() {
        return new ChatServer();
    }

    @Bean
    public WebSocketHandler hanoiHandler() {
        return new HanoiWebSocketHandler(gameHistoryService, aggregateService, hanoi());
    }
}