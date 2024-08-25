package com.chikanov.gstore.websock.service;

import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.enums.TypesOfMessage;
import com.chikanov.gstore.exceptions.WsException;
import com.chikanov.gstore.exceptions.enums.WsExceptionType;
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




    public void switchController(Message message) throws WsException{
            switch (message.tos()) {
                case AUTH -> authenticationMessage(message);
                case ACTION -> wsRoomService.actionMessageToRoom(message);
                case RECONNECT -> wsRoomService.reconnectHandler(message);
            }
    }
    public void newConnection(WebSocketSession session) throws WsException
    {
        wsAuthenticationService.addToWaitList(session);

    }
    public void disconnectHandler(WebSocketSession session, CloseStatus closeStatus) throws WsException {
            wsRoomService.closeConnection(session, closeStatus.getCode());
    }

    private void authenticationMessage(Message message) throws WsException{
        try {
        AuthenticationMessage authMessage = objectMapper.readValue(message.payload(), AuthenticationMessage.class);
        User user =  wsAuthenticationService.authenticate(authMessage);
        if (message.session().getId().equals(message.session().getId())){
            wsRoomService.addUser(authMessage.game(), user, message.session());
        }
        }catch (JsonProcessingException jsonProcessingException){
            throw new WsException(jsonProcessingException.getMessage(),WsExceptionType.INVALID_JSON);
        }

    }

}
