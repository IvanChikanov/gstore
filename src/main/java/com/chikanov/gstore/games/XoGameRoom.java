package com.chikanov.gstore.games;

import com.chikanov.gstore.entity.ChatRoles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class XoGameRoom extends AbstractWsGameRoom {

    private final Integer[] symbol = new Integer[]{1, 2};
    private final Set<XoPlayer> players = new HashSet<>();
    private final int[] cells = new int[9];

    private final ObjectMapper objectMapper = new ObjectMapper();

    private void startGame(){
        Arrays.fill(cells, 0);
        players.forEach(xoPlayer -> xoPlayer.sendMessageToUser(new TextMessage("start")));
    }

    private JsonNode messageParse(WebSocketMessage<String> message) throws JsonProcessingException{

        JsonNode json = objectMapper.readTree(message.getPayload());
        return  json;
    }

    @Override
    public void addUser(WebSocketSession session, ChatRoles user) throws IOException {
        if(players.size() < getMax())
        {
            players.add(new XoPlayer(session, user));
            if (players.size() == getMax())
                startGame();
        }
        else{
            session.close(CloseStatus.TOO_BIG_TO_PROCESS);
        }
    }

    @Override
    public void readMessage(WebSocketSession user, WebSocketMessage<?> message) throws JsonProcessingException{
        JsonNode json = objectMapper.readTree(message.getPayload().toString());
        cells[json.get("action").asInt()] = json.get("who").asInt();

        players.stream().
                filter(xoPlayer -> xoPlayer.userSession != user).
                forEach(xoPlayer -> xoPlayer.sendMessageToUser(message));
    }

    @Override
    public int getMax(){
       return 2;
    }

    private class XoPlayer extends Player{
        protected XoPlayer(WebSocketSession session, ChatRoles user) {
            super(session, user);
            super.sendMessageToUser(new TextMessage(symbol[players.size()].toString()));
        }
    }
}
