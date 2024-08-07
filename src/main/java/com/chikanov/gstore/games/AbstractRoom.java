package com.chikanov.gstore.games;

import com.chikanov.gstore.entity.ChatRole;
import com.chikanov.gstore.records.ActionMessage;
import com.chikanov.gstore.records.WsPlayer;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractRoom<T> implements IRoom{

    protected UUID roomId;
    protected Map<UUID, T> players;
    protected int max;

    public AbstractRoom(UUID id){
        roomId = id;
        players = new HashMap<>();
    }

    @Override
    public abstract boolean addUser(WsPlayer player) throws IOException;

    @Override
    public abstract void readMessage(ActionMessage message) throws Exception;

    @Override
    public void sendMessage(WebSocketSession user, WebSocketMessage<?> message) {

    }

    @Override
    public void closeConnection(WebSocketSession user) {

    }

}
