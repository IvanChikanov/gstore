package com.chikanov.gstore.websock.service;

import com.chikanov.gstore.records.ActionMessage;
import com.chikanov.gstore.records.AuthenticationMessage;
import com.chikanov.gstore.records.WsPlayer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

@Service
public class WSRouteService {

    @Autowired
    private WSAuthenticationService wsAuthenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WSRoomService wsRoomService;


    public void switchController(String message) throws Exception{
        String[] cutMessage = message.split(":::");
        if(cutMessage.length == 2) {
            switch (cutMessage[0]) {
                case "AUTH" -> authenticationMessage(cutMessage[1]);
                case "ACTION" -> gameEvent(cutMessage[1]);
            }
        }
    }
    public void newConnection(WebSocketSession session) throws Exception
    {
        wsAuthenticationService.addToWaitList(session);

    }
    private void authenticationMessage(String message) throws Exception{
        AuthenticationMessage authMessage = objectMapper.readValue(message, AuthenticationMessage.class);
        WsPlayer user =  wsAuthenticationService.authenticate(authMessage);
        addUserToRoom(user, authMessage.game());
    }

    private void addUserToRoom(WsPlayer player, UUID target) throws Exception{
            wsRoomService.addUser(target, player);
    }
    private void gameEvent(String message) throws Exception{
        JsonNode json = objectMapper.readTree(message);
        JsonNode action = json.get("action");
        ActionMessage actionMessage = new ActionMessage(
                UUID.fromString(json.get("from").asText()),
                UUID.fromString(json.get("game").asText()),
                objectMapper.writeValueAsString(action)
        );
        wsRoomService.actionMessageToRoom(actionMessage);
    }

}
