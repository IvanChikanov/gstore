package com.chikanov.gstore.websock.service;

import com.chikanov.gstore.games.IRoom;
import com.chikanov.gstore.games.XoGameRoom;
import com.chikanov.gstore.records.ActionMessage;
import com.chikanov.gstore.records.WsPlayer;
import com.chikanov.gstore.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WSRoomService {

    @Autowired
    private GameService gameService;

    private final ConcurrentHashMap<UUID, IRoom> rooms = new ConcurrentHashMap<>();

    public void addUser(UUID gameId, WsPlayer player) throws Exception
    {
        if(!rooms.containsKey(gameId))
            rooms.put(gameId, switchGameTypes(gameId));
        rooms.get(gameId).addUser(player);
    }

    private IRoom switchGameTypes(UUID id){
        String module = gameService.getGameModule(id);
        return switch (module){
            case "XO" -> new XoGameRoom(id);
            default -> throw new RuntimeException("module not find!");
        };
    }

    public void actionMessageToRoom(ActionMessage message) throws Exception{
        rooms.get(message.game()).readMessage(message);
    }
}
