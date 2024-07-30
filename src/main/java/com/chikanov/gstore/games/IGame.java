package com.chikanov.gstore.games;

import com.chikanov.gstore.entity.ChatRole;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public interface IGame {

    int getMax();
    boolean readMessage(WebSocketSession user, WebSocketMessage<?> message) throws IOException;
    void sendMessage(String message) throws IOException;
    boolean addUser(WebSocketSession session, ChatRole user);

}
