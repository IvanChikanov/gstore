package com.chikanov.gstore.websock;

import com.chikanov.gstore.entity.ChatRoles;
import com.chikanov.gstore.games.IGame;
import com.chikanov.gstore.games.impl.XoGame;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@Service
public class WebSocketGameService {

    private HashMap<UUID, IGame> rooms = new HashMap<>();

    public synchronized void newUser(WebSocketSession user, UUID gameId) throws IOException {

        if(rooms.containsKey(gameId)){
            if(!rooms.get(gameId).addUser(user, new ChatRoles())){
                user.close(CloseStatus.SERVICE_OVERLOAD);
            }/////Z ZZ ZZZ ZZ Z
        }
        else{
            XoGame gameRoom = new XoGame();
            gameRoom.addUser(user, new ChatRoles());/////Z ZZ ZZZ ZZ Z
            rooms.put(gameId, gameRoom);
            System.out.println("g " + rooms.size());
        }
    }
    public synchronized void message(UUID gameId, WebSocketSession user, WebSocketMessage<?> message){
        try {
            rooms.get(gameId).readMessage(user, message);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
