package com.chikanov.gstore.games;

import com.chikanov.gstore.entity.ChatRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public abstract class AbstractWsGameRoom {

    protected int online;

    public abstract void addUser(WebSocketSession session, ChatRole user) throws IOException;
    public abstract void readMessage(WebSocketSession user, WebSocketMessage<?> message) throws JsonProcessingException;
    public abstract int getMax();

    protected abstract class Player{

        protected WebSocketSession userSession;
        protected ChatRole systemUser;

        protected Player(WebSocketSession session, ChatRole user)
        {
            userSession = session;
            systemUser = user;
        }
        protected void sendMessageToUser(WebSocketMessage<?> message)
        {
            try {
                userSession.sendMessage(message);
            }
            catch (IOException ioex){
                ioex.printStackTrace();
            }
        }


    }
}
