package com.chikanov.gstore.games.interfaces;

import com.chikanov.gstore.records.*;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;

public interface IRoom {

    boolean addUser(WsPlayer player) throws IOException;
    void action(Message message);
    void disconnect(Message message);
}
