package com.chikanov.gstore.games;

import com.chikanov.gstore.entity.ChatRoles;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public abstract class AbstractWsGameRoom {

    protected int online;

    public abstract void addUser(WebSocketSession session, ChatRoles user);
    public abstract void readMessage(WebSocketSession user, WebSocketMessage<?> message);
    public abstract int getMax();

    @NoArgsConstructor
    protected abstract class Player{

        protected WebSocketSession userSession;
        protected ChatRoles systemUser;

        protected Player(WebSocketSession session, ChatRoles user)
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
