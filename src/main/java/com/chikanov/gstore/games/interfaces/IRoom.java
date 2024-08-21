package com.chikanov.gstore.games.interfaces;

import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.exceptions.WsException;
import com.chikanov.gstore.records.*;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public interface IRoom {

    void addUser(User user,  WebSocketSession session) throws WsException;
    void action(Message message) throws WsException;
    void disconnect(WebSocketSession session) throws WsException;
}
