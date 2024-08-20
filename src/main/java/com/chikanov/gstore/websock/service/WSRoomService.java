package com.chikanov.gstore.websock.service;

import com.chikanov.gstore.entity.Game;
import com.chikanov.gstore.entity.Result;
import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.games.interfaces.IRoom;
import com.chikanov.gstore.games.components.XoGameRoom;
import com.chikanov.gstore.records.ActionMessage;
import com.chikanov.gstore.records.CloseMessage;
import com.chikanov.gstore.records.Message;
import com.chikanov.gstore.records.WsPlayer;
import com.chikanov.gstore.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class WSRoomService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private GameService gameService;

    private final ConcurrentHashMap<UUID, IRoom> rooms = new ConcurrentHashMap<>();
    private final Map<String, UUID> users = new ConcurrentHashMap<>();

    public void addUser(UUID gameId, User user,  WebSocketSession session) throws Exception
    {
        if(!rooms.containsKey(gameId)) {
            rooms.put(gameId, switchGameTypes(gameId));
        }
        users.put(session.getId(), gameId);
        rooms.get(gameId).addUser(user, session);
    }

    private IRoom switchGameTypes(UUID id){
        Game game = gameService.getGame(id);
        return switch (game.getGameType().getModule()){
            case "XO" -> applicationContext.getBean(XoGameRoom.class, game);
            default -> throw new RuntimeException("module not find!");
        };
    }

    @Transactional
    public void actionMessageToRoom(Message message) throws Exception{
        UUID game = users.get(message.session().getId());
        rooms.get(game).action(message);
    }

    public void closeConnection(CloseMessage reason){

    }
    @EventListener
    public void deleteGame(List<Result> res){
        System.out.println("Удаляю Команту");
    }
}
