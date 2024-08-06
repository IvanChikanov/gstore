package com.chikanov.gstore.websock.service;

import com.chikanov.gstore.entity.ChatRole;
import com.chikanov.gstore.games.AbstractWsGameRoom;
import com.chikanov.gstore.games.XoGameRoom;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketGameService {

    private final ConcurrentHashMap<UUID, Set<WebSocketSession>> waitedUsers = new ConcurrentHashMap<>();
    private HashMap<UUID, AbstractWsGameRoom> rooms = new HashMap<>();

    public void connectNewUser(UUID gameId, WebSocketSession user)
    {
        if(!waitedUsers.containsKey(gameId))
            waitedUsers.put(gameId, new HashSet<>());
    }
    public synchronized void newUser(WebSocketSession user, UUID gameId) throws IOException {

        if(!rooms.containsKey(gameId))
        {
//            XoGameRoom gameRoom = new XoGameRoom();
//            rooms.put(gameId, gameRoom);
        }
        rooms.get(gameId).addUser(user, new ChatRole());
    }
    public synchronized void message(UUID gameId, WebSocketSession user, WebSocketMessage<?> message){
        try {
            rooms.get(gameId).readMessage(user, message);
        }
        catch (JsonProcessingException jsonEx){
            System.out.println(jsonEx.getMessage());
        }
    }
}
