package com.chikanov.gstore.websock.service;

import com.chikanov.gstore.enums.TypesOfMessage;
import com.chikanov.gstore.records.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WSRouteService {

    @Autowired
    private WSAuthenticationService wsAuthenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WSRoomService wsRoomService;




    public void switchController(WebSocketSession session, String message) throws Exception{
        Message m = rawMessageHandler(session, message);
            switch (m.tos()) {
                case AUTH -> authenticationMessage(session, m.payload());
                case ACTION -> wsRoomService.actionMessageToRoom(m);
            }
    }
    public void newConnection(WebSocketSession session) throws Exception
    {
        wsAuthenticationService.addToWaitList(session);

    }
    public void disconnectHandler(CloseStatus closeStatus) throws JsonProcessingException {
        if(closeStatus.getCode() == 3001){
            wsRoomService.closeConnection(objectMapper.readValue(closeStatus.getReason(), CloseMessage.class));
        }
    }

    private void authenticationMessage(WebSocketSession session, String message) throws Exception{
        AuthenticationMessage authMessage = objectMapper.readValue(message, AuthenticationMessage.class);
        WsPlayer user =  wsAuthenticationService.authenticate(authMessage);
        if (user.session().equals(session)){
            wsRoomService.addUser(authMessage.game(), user);
        }

    }

    private Message rawMessageHandler(WebSocketSession session, String message){
        String[] cutPayload = message.split(":::");
        String[] types = cutPayload[0].split(":");
        return new Message(TypesOfMessage.valueOf(types[0]), Integer.parseInt(types[1]), cutPayload[1], session);
    }

}
