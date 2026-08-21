package com.trade.app.upstock.lib.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.trade.app.upstock.lib.websocket.UiWebSocketHandler;

@Configuration
@EnableWebSocket
public class MarketWebSocketConfig implements WebSocketConfigurer {

    private final UiWebSocketHandler uiWebSocketHandler;

    public MarketWebSocketConfig(
            UiWebSocketHandler uiWebSocketHandler) {
        this.uiWebSocketHandler = uiWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(
            WebSocketHandlerRegistry registry) {

        registry.addHandler(
                uiWebSocketHandler,
                "/market-feed")
                .setAllowedOrigins("*");
    }

}