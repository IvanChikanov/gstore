package com.chikanov.gstore.games;

import com.chikanov.gstore.entity.ChatRole;
import com.chikanov.gstore.records.WsPlayer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class XoGameRoom extends AbstractRoom<XoGameRoom.XoPlayer> {

    private final Integer[] symbol = new Integer[]{1, 2};
    private final int[] cells = new int[9];

    private final ObjectMapper objectMapper = new ObjectMapper();
    public XoGameRoom(UUID id){
        super(id);
        max = 2;
        Arrays.fill(cells, 0);
    }


    private void startGame(){

        //players.forEach(xoPlayer -> xoPlayer.sendMessageToUser(new TextMessage("start")));
    }

    private JsonNode messageParse(WebSocketMessage<String> message) throws JsonProcessingException{

        JsonNode json = objectMapper.readTree(message.getPayload());
        return  json;
    }

    @Override
    public boolean addUser(WsPlayer player) throws IOException {
        if(players.size() < max)
        {
            players.put(player.wsUser().externalId(), new XoPlayer(player, symbol[players.size()], false));
            if (players.size() == max)
                startGame();
            return true;
        }
        else{
            player.wsUser().session().close(CloseStatus.TOO_BIG_TO_PROCESS);
            return false;
        }
    }

    @Override
    public void readMessage(WebSocketSession user, WebSocketMessage<?> message){
        System.out.println(message.getPayload());
//        JsonNode json = objectMapper.readTree(message.getPayload().toString());
//        cells[json.get("action").asInt()] = json.get("who").asInt();
//
//        players.stream().
//                filter(xoPlayer -> xoPlayer.userSession != user).
//                forEach(xoPlayer -> xoPlayer.sendMessageToUser(message));
    }

    public record XoPlayer(WsPlayer wsPlayer, int number, boolean winner){}
}
