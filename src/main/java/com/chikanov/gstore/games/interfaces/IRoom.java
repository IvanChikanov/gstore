package com.chikanov.gstore.games.interfaces;

import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.records.*;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public interface IRoom {

    boolean addUser(User user,  WebSocketSession session) throws IOException;
    void action(Message message) throws Exception;
    void disconnect(Message message);
}
