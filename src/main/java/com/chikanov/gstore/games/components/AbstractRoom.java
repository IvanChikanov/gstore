package com.chikanov.gstore.games.components;

import com.chikanov.gstore.entity.Game;
import com.chikanov.gstore.enums.TypesOfMessage;
import com.chikanov.gstore.games.interfaces.IRoom;
import com.chikanov.gstore.records.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
public abstract class AbstractRoom<T> implements IRoom {

    protected Game game;
    protected Map<String, T> players;
    protected int max;

    public AbstractRoom(Game g){
        this.game = g;
        players = new HashMap<>();
    }

    public String createMessage(TypesOfMessage type, int number, String body){
        return String.format("%s:%d:::%s", type, number,body);
    }
}
