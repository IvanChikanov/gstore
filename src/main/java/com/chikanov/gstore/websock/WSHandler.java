package com.chikanov.gstore.websock;

import com.chikanov.gstore.websock.messages.WsMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Service
public class WSHandler implements WebSocketHandler {

    @Autowired
    private WebSocketGameService socketService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        MultiValueMap<String, String> params = UriComponentsBuilder.fromUri(session.getUri()).build().getQueryParams();
        socketService.newUser(session, UUID.fromString(params.getFirst("game_id")));
        var mess = objectMapper.readValue(
                "{\"type\":\"auth\",\"from\":\"russia\",\"game\":\""
                        + UUID.randomUUID() +
                        "\",\"payload\":{\"token\":\"ewrwesfdsadfsdfasdfagsjdfg askjfd\"}}",
                WsMessage.class);
        System.out.println(mess.getClass());
        System.out.println(mess.getPayload().getClass());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        MultiValueMap<String, String> params = UriComponentsBuilder.fromUri(session.getUri()).build().getQueryParams();
        socketService.message( UUID.fromString(params.getFirst("game_id")), session, message);
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
