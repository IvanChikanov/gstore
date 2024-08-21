package com.chikanov.gstore.websock.service;

import com.chikanov.gstore.exceptions.WsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WSHandler implements WebSocketHandler {

    @Autowired
    private WSRouteService webSocketRouteService;

    @Autowired
    private WsMessageConverter wsMessageConverter;


    private final ConcurrentHashMap<WebSocketSession, UUID> unauthorizedSessions = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        webSocketRouteService.newConnection(session);

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        try {
            webSocketRouteService.switchController(
                    wsMessageConverter.createMessage(session, message.getPayload().toString())
            );
        }
        catch (WsException exception){
            try {
                session.sendMessage(new TextMessage(exception.getMessage()));
                session.close(exception.status);
            }
            catch (IOException ioException){
                System.out.println(ioException.getMessage());
            }
        }

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

       System.out.println(session.getRemoteAddress());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        webSocketRouteService.disconnectHandler(closeStatus);

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
