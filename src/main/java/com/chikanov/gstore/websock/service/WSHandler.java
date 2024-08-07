package com.chikanov.gstore.websock.service;

import com.chikanov.gstore.records.AuthorizeMessage;
import com.chikanov.gstore.websock.messages.WsMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WSHandler implements WebSocketHandler {

    @Autowired
    private WebSocketGameService socketService;

    @Autowired
    private WebSocketRouteService webSocketRouteService;

    private final ConcurrentHashMap<WebSocketSession, UUID> unauthorizedSessions = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        webSocketRouteService.newConnection(session);

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        webSocketRouteService.switchController(message.getPayload().toString());

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
       exception.printStackTrace();
       System.out.println();
       System.out.println(exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
