package com.chikanov.gstore.games.impl;

import com.chikanov.gstore.entity.ChatRoles;
import com.chikanov.gstore.games.IGame;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class XoGame implements IGame {

    private final Set<WebSocketSession> users = new HashSet<>();

    @Override
    public boolean readMessage(WebSocketSession user, WebSocketMessage<?> message) throws IOException {
        var mes = message.getPayload();
        System.out.println(mes);
        sendMessage();
        return false;
    }
    @Override
    public int getMax()
    {
        return 2;
    }
    @Override
    public void sendMessage() throws IOException {
            for(var user : users){
                user.sendMessage(new TextMessage("hoh- hhoho " + users.size()   ));
            }
    }

    @Override
    public boolean addUser(WebSocketSession session, ChatRoles user) {
        users.add(session);
        return true;
    }
}
