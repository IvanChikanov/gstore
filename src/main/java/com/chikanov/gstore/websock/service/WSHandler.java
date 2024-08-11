package com.chikanov.gstore.websock.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WSHandler implements WebSocketHandler {

    @Autowired
    private WSRouteService webSocketRouteService;


    private final ConcurrentHashMap<WebSocketSession, UUID> unauthorizedSessions = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        webSocketRouteService.newConnection(session);

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        System.out.println(message.getPayload());
        try {
            webSocketRouteService.switchController(message.getPayload().toString());

        }
        catch (Exception ex){
                ex.printStackTrace();
        }

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

       System.out.println(session.getRemoteAddress());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println(closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
