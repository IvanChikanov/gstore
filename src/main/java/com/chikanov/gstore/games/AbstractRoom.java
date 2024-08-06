package com.chikanov.gstore.games;

import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.UUID;

public class AbstractRoom<T> implements IRoom{

    protected Map<WebSocketSession, T> players;

    @Override
    public void createRoom(UUID gameId) {

    }

    @Override
    public boolean addUser(WebSocketSession user) {
        return false;
    }

    @Override
    public void readMessage(WebSocketSession user, WebSocketMessage<?> message) {

    }

    @Override
    public void sendMessage(WebSocketSession user, WebSocketMessage<?> message) {

    }

    @Override
    public void closeConnection(WebSocketSession user) {

    }
}
