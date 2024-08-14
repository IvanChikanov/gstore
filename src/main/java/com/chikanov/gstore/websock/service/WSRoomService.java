package com.chikanov.gstore.websock.service;

import com.chikanov.gstore.entity.Game;
import com.chikanov.gstore.entity.Result;
import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.games.IRoom;
import com.chikanov.gstore.games.XoGameRoom;
import com.chikanov.gstore.records.ActionMessage;
import com.chikanov.gstore.records.CloseMessage;
import com.chikanov.gstore.records.WsPlayer;
import com.chikanov.gstore.repositories.UserRepository;
import com.chikanov.gstore.services.GameService;
import com.chikanov.gstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class WSRoomService {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

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

    @Transactional
    public void actionMessageToRoom(ActionMessage message) throws Exception{
        IRoom room = rooms.get(message.game());
        if(room.readMessage(message)){
            var results = room.endGame();
            Game game = gameService.getGame(message.game());
            game.setResults(results.stream().map(res ->{
                var r =  new Result();
                User user = userService.getById(res.user());
                r.setUser(user);
                r.setWinner(res.winner());
                r.setPoints(res.points());
                r.setGame(game);
                return r;
            }).collect(Collectors.toSet()));
            gameService.saveGame(game);
        }
    }

    public void closeConnection(CloseMessage reason){
        if(rooms.get(reason.game()).closeConnection(reason)){
            rooms.remove(reason.game());
        }
    }
}
