package com.chikanov.gstore.games;

import com.chikanov.gstore.entity.ChatRoles;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

public class XoGameRoom extends AbstractWsGameRoom {

    private Map<WebSocketSession, XoPlayer> players = new HashMap<>();

    public XoGameRoom(){
        for(int i = 0; i < getMax(); i++){

        }
    }

    @Override
    public void addUser(WebSocketSession session, ChatRoles user) {
        if(online < getMax()){

        }
    }

    @Override
    public void readMessage(WebSocketSession user, WebSocketMessage<?> message) {

    }

    @Override
    public int getMax(){
       return 2;
    }

    private class XoPlayer extends Player{
        protected XoPlayer() {
            super();
        }
    }
}
