package com.chikanov.gstore.games.impl;

import com.chikanov.gstore.entity.ChatRole;
import com.chikanov.gstore.games.IGame;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class XoGame implements IGame {

    private final Set<WebSocketSession> users = new HashSet<>();

    private final int[] cells = new int[9];
    @Override
    public boolean readMessage(WebSocketSession user, WebSocketMessage<?> message) throws IOException {
        var mes = message.getPayload();
        System.out.println(mes);
        return false;
    }
    @Override
    public int getMax()
    {
        return 2;
    }
    @Override
    public void sendMessage(String message) {

            for (var user : users) {
                sendOneUserMessage(user, message);
            }
    }

    @Override
    public boolean addUser(WebSocketSession session, ChatRole user) {
        if(users.size() < getMax()){
            users.add(session);
            sendOneUserMessage(session, String.valueOf(users.size()));
            if(users.size() == getMax())
            {
                this.sendMessage("start");
            }
            return true;
        }
        return false;
    }
    private void sendOneUserMessage(WebSocketSession user, String message)
    {
        try {
            user.sendMessage(new TextMessage(message));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }


}
