package com.chikanov.gstore.games.components;

import com.chikanov.gstore.entity.Game;
import com.chikanov.gstore.enums.TypesOfMessage;
import com.chikanov.gstore.games.interfaces.IRoom;
import com.chikanov.gstore.games.objects.Player;
import com.chikanov.gstore.records.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public abstract class AbstractRoom<T> implements IRoom {

    protected Game game;
    protected Map<String, Player<T>> players;
    protected int max;

    @Autowired
    protected ApplicationEventPublisher eventPublisher;

    public AbstractRoom(Game g){
        this.game = g;
        players = new ConcurrentHashMap<>();
    }

}
