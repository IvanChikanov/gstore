package com.chikanov.gstore.configuration;

import com.chikanov.gstore.websock.WSHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.util.Map;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WSHandler wsHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry.addHandler(wsHandler, "/connection").setAllowedOrigins("*").setHandshakeHandler(new Shaker());
    }
    public class Shaker implements HandshakeHandler{

        @Override
        public boolean doHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws HandshakeFailureException {
            System.out.println(request.getHeaders());
            System.out.println(response.getHeaders());
            System.out.println(attributes.size());
            attributes.forEach((s, o) -> {
                System.out.println(s);
                System.out.println(o);
            });

            return true;
        }
    }
}
