package com.chikanov.gstore.websock.service;

import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.entity.tgentities.TgUser;
import com.chikanov.gstore.filters.Authenticator;
import com.chikanov.gstore.records.AuthData;
import com.chikanov.gstore.records.AuthenticationMessage;
import com.chikanov.gstore.records.WsPlayer;
import com.chikanov.gstore.records.WsUser;
import com.chikanov.gstore.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WSAuthenticationService {

    @Autowired
    private Authenticator authenticator;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private final ConcurrentHashMap<UUID, WebSocketSession> unauthorizedSessions = new ConcurrentHashMap<>();

    public WsPlayer authenticate(AuthenticationMessage authMessage) throws JsonProcessingException {
        AuthData auth = authenticator.validation(authMessage.token());
        if(auth.statusCode().equals(HttpStatus.OK)) {
            User user =  userService.getOrCreate(objectMapper.readValue(auth.result(), TgUser.class));
            var sess = unauthorizedSessions.remove(authMessage.from());
            WsUser wsUser = new WsUser(authMessage.from(), sess);
            return new WsPlayer(wsUser, user);
        }
        throw new RuntimeException("token not valid");
    }

    public void addToWaitList(WebSocketSession session) throws Exception{
        UUID createdID = UUID.randomUUID();
        unauthorizedSessions.put(createdID, session);
        session.sendMessage(new TextMessage(createdID.toString()));
        System.out.println("authMessageSended");
    }
}
