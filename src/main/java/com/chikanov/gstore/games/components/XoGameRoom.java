package com.chikanov.gstore.games.components;

import com.chikanov.gstore.entity.Game;
import com.chikanov.gstore.entity.Result;
import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.records.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class XoGameRoom extends AbstractRoom<WsPlayer> {

    private final Integer[] symbol = new Integer[]{1, 2};
    private final int size;
    private final int[] cells;
    private List<int[]> lines;

    private final ObjectMapper objectMapper = new ObjectMapper();
    public XoGameRoom(Game g){
        super(g);
        size = 3;
        max = 2;
        cells = new int[size * size];
        lines = filler(size);
        Arrays.fill(cells, 0);
    }


    private void startGame() throws IOException{
        for(var player : players.values()){
            player.session().sendMessage(new TextMessage("start"));
        }
    }
    private void sendAllBut(String id, Message message) throws Exception
    {
        for(var key: players.keySet()){
            if(key != id){
                players.get(key).session().sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
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
    private Finish checkResults(int number){
        List<Integer> indexesToDelete = new ArrayList<>();
        int count = 0;
        for(int[] line: lines){
            boolean find = true;
            for(int index : line){
                if(cells[index] != number){
                    find = false;
                    if(maybeWin(line, number)){
                        indexesToDelete.add(count);
                    }
                    break;
                }
            }
            if(find){
                return new Finish(true, true);
            }
            count++;
        }
        indexesToDelete.forEach(i -> lines.remove((int)i));
        return lines.isEmpty() ? new Finish(false, false) : new Finish(false, true);
    }
    private boolean maybeWin(int[] line, int number){
        for(int i : line){
            if(cells[i] != 0 || cells[i] != number){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addUser(WsPlayer player) throws IOException {
        if(players.size() < max)
        {
            players.put(player.session().getId(), player);
            game.
            if (players.size() == max)
                startGame();
            return true;
        }
        else{
            player.session().close(new CloseStatus(4005, "Комната переполнена"));
            return false;
        }
    }

    @Override
    public void action(Message message) {

    }

    @Override
    public void disconnect(Message message) {

    }

    @Override
    public boolean readMessage(Message message) throws Exception{
        Index index = objectMapper.readValue(message.jsonAction(), Index.class);
        XoPlayer player = players.get(message.from());
        if(index.index() >= 0)
            cells[index.index()] = player.number;

        Finish result = checkResults(player.number());
        if(!result.winner()){
            if(result.may()) {
                sendAllBut(player.wsPlayer.wsUser().externalId(),
                        new Message(false, index.index(), false, player.number()));
                return false;
            }
            else{
                sendAllBut(UUID.randomUUID(), new Message(true, 0, false, 0));
                return true;
            }
        }
        else{
            players.put(message.from(), new XoPlayer(player.wsPlayer(), player.number(), true));
            sendAllBut(player.wsPlayer.wsUser().externalId(),
                    new Message(true, index.index(), false, player.number()));
            player.wsPlayer.wsUser().session().sendMessage(new TextMessage(objectMapper.writeValueAsString(
                    new Message(true, index.index(), true, player.number())
            )));
            return true;
        }
    }

    public Set<ResultData> endGame()
    {
        return players.values().stream().map(player ->
                new ResultData(player.wsPlayer.dbUser(), player.winner ? 1 : 0,
                        player.winner())).collect(Collectors.toSet());
    }

    @Override
    public boolean closeConnection(CloseMessage message) {
        players.remove(message.from());
        return players.isEmpty();
    }

    public record XoPlayer(WsPlayer wsPlayer, int number, boolean winner){}
    private record Index(int index){}
    private record Finish(boolean winner, boolean may){}
}
