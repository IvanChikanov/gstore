package com.chikanov.gstore.configuration;

import com.chikanov.gstore.websock.WSHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WSHandler wsHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry.addHandler(wsHandler, "/connection").setAllowedOrigins("*").setHandshakeHandler(new DefaultHandshakeHandler());
    }
}
