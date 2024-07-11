package com.chikanov.gstore.websock;

import com.chikanov.gstore.entity.ChatRoles;
import com.chikanov.gstore.games.AbstractWsGameRoom;
import com.chikanov.gstore.games.IGame;
import com.chikanov.gstore.games.XoGameRoom;
import com.chikanov.gstore.games.impl.XoGame;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@Service
public class WebSocketGameService {

    private HashMap<UUID, AbstractWsGameRoom> rooms = new HashMap<>();

    public synchronized void newUser(WebSocketSession user, UUID gameId) throws IOException {

        if(!rooms.containsKey(gameId))
        {
            XoGameRoom gameRoom = new XoGameRoom();
            rooms.put(gameId, gameRoom);
        }
        rooms.get(gameId).addUser(user, new ChatRoles());
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
