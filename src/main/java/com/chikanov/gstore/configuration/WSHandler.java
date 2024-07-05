package com.chikanov.gstore.configuration;

import org.springframework.web.socket.*;

public class WSHandler implements WebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println(session.getLocalAddress());
        System.out.println(session.getRemoteAddress());
        System.out.println(session.getUri());
        session.sendMessage( new TextMessage("hello"));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println(session.getUri());
        System.out.println(message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
