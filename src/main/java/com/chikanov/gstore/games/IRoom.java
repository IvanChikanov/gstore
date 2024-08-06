package com.chikanov.gstore.games;

import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

public interface IRoom {

    void createRoom(UUID gameId);

    boolean addUser(WebSocketSession user);

    void readMessage(WebSocketSession user, WebSocketMessage<?> message);

    void sendMessage(WebSocketSession user, WebSocketMessage<?> message);

    void closeConnection(WebSocketSession user);
}
