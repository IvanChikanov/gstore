package com.chikanov.gstore.games;

import com.chikanov.gstore.entity.ChatRole;
import com.chikanov.gstore.records.ActionMessage;
import com.chikanov.gstore.records.WsPlayer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

public class XoGameRoom extends AbstractRoom<XoGameRoom.XoPlayer> {

    private final Integer[] symbol = new Integer[]{1, 2};
    private final int size;
    private final int[] cells;
    private List<int[]> lines;

    private final ObjectMapper objectMapper = new ObjectMapper();
    public XoGameRoom(UUID id){
        super(id);
        size = 3;
        max = 2;
        cells = new int[size * size];
        lines = filler(size);
        Arrays.fill(cells, 0);
    }


    private void startGame() throws IOException{
        for(var player : players.values()){
            player.wsPlayer.wsUser().session().sendMessage(new TextMessage("start"));
        }
    }
    private void sendAllBut(UUID id, Message message) throws Exception
    {
        for(var key: players.keySet()){
            if(key != id){
                players.get(key).wsPlayer.wsUser().session().sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
            }
        }
    }

    private List<int[]> filler(int size)
    {
        List<int[]> lines = new ArrayList<>();
        int[] right = new int[size];
        int[] left = new int[size];
        for (int col = 0; col < size; col++) {
            int[] rows = new int[size];
            int[] cols = new int[size];
            for (int row = 0; row < size; row++) {
                rows[row] = col * size + row;
                cols[row] = row * size + col;
            }
            lines.add(rows);
            lines.add(cols);
            right[col] = col * size + col;
            left[col] = col * size + (size - 1 - col);
        }
        lines.add(right);
        lines.add(left);
        return lines;
    }
    private boolean checkResults(int number){
        for(int[] line: lines){
            boolean find = true;
            for(int index : line){
                if(cells[index] != number){
                    find = false;
                    break;
                }
            }
            if(find){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addUser(WsPlayer player) throws IOException {
        if(players.size() < max)
        {
            XoPlayer xop =  new XoPlayer(player, symbol[players.size()], false);
            players.put(player.wsUser().externalId(), xop);
            player.wsUser().session().sendMessage(new TextMessage(String.valueOf(xop.number)));
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
    public void readMessage(ActionMessage message) throws Exception{
        System.out.println(message.jsonAction());
        Index index = objectMapper.readValue(message.jsonAction(), Index.class);
        XoPlayer player = players.get(message.from());
        cells[index.index()] = player.number;
        boolean result = checkResults(player.number());
        if(!result){
            sendAllBut(player.wsPlayer.wsUser().externalId(),
                    new Message(false, index.index(), false, player.number()));
        }
        else{
            sendAllBut(player.wsPlayer.wsUser().externalId(),
                    new Message(true, index.index(), false, player.number()));
            player.wsPlayer.wsUser().session().sendMessage(new TextMessage(objectMapper.writeValueAsString(
                    new Message(true, index.index(), true, player.number())
            )));
        }

    }

    public record XoPlayer(WsPlayer wsPlayer, int number, boolean winner){}
    private record Index(int index){}
    private record Message(boolean finish, int index, boolean win, int playerNumber){}
}
