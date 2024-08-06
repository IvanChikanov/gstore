package com.chikanov.gstore.games;

import com.chikanov.gstore.records.WsPlayer;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.UUID;

public interface IRoom {

    boolean addUser(WsPlayer player) throws IOException;

    void readMessage(WebSocketSession user, WebSocketMessage<?> message);

    void sendMessage(WebSocketSession user, WebSocketMessage<?> message);

    void closeConnection(WebSocketSession user);
}
