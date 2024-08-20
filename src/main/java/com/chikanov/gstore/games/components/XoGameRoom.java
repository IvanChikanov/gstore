package com.chikanov.gstore.games.components;

import com.chikanov.gstore.entity.Game;
import com.chikanov.gstore.entity.Result;
import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.enums.TypesOfMessage;
import com.chikanov.gstore.games.objects.Player;
import com.chikanov.gstore.records.*;
import com.chikanov.gstore.services.UserService;
import com.chikanov.gstore.websock.service.WsMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class XoGameRoom extends AbstractRoom<XoGameRoom.XoPlayer> {

    @Autowired
    private WsMessageConverter wsMessageConverter;

    @Autowired
    private UserService userService;

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
        int count = 0;
        int randomNumber = 0;

        for(var player : players.values()){

            count++;
            randomNumber = count < 2 ?
                new Random().nextInt(1, 3):
                randomNumber == 1 ? 2 : 1;
            player.getRealTimeData().number = randomNumber;
            player.getSession().sendMessage(new TextMessage(
                    wsMessageConverter.createFullMessage(
                            TypesOfMessage.START, 0,
                            String.valueOf(randomNumber))
                    )
            );
        }
    }
    private void sendAllBut(String id, String message) throws Exception
    {
        for(var key: players.keySet()){
            if(!Objects.equals(key, id)){
                players.get(key).getSession().sendMessage(new TextMessage(message));
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
        for(int[] line: lines.stream().sorted().toList()){
            boolean find = true;
            for(int index : line){
                if(cells[index] != number){
                    find = false;
                    if(!maybeWin(line, number)){
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
        indexesToDelete.stream().sorted(Collections.reverseOrder()).forEach(i -> lines.remove((int)i));
        return lines.isEmpty() ? new Finish(false, false) : new Finish(false, true);
    }
    private boolean maybeWin(int[] line, int number){
        boolean res = true;
        for(int i : line){
            if(cells[i] != 0 && cells[i] != number){
                res = false;
                break;
            }
        }
        return res;
    }

    @Override
    public boolean addUser(User user, WebSocketSession session) throws IOException {
        if(players.size() < max)
        {
            Player<XoPlayer> player = new Player<>();
            Result result = new Result();
            result.setGame(game);
            result.setUser(user);
            player.setUser(user);
            player.setSession(session);
            player.setRealTimeData(new XoPlayer());
            player.getRealTimeData().setResult(result);
            players.put(session.getId(), player);
            if (players.size() == max)
                startGame();
            return true;
        }
        else{
            session.close(new CloseStatus(4005, "Комната переполнена"));
            return false;
        }
    }

    @Override
    public void action(Message message) throws Exception{
        int index = Integer.parseInt(message.payload());
        int number = players.get(message.session().getId()).getRealTimeData().number;
        if(index >= 0)
            cells[index] = number;
        Finish result = checkResults(number);
        if(result.may()) {
            if (!result.winner()) {
                sendAllBut(message.session().getId(),
                        wsMessageConverter.createFullMessage(TypesOfMessage.ACTION, number, String.valueOf(index)));
            }
            else{
                for(var x : players.values()){
                    if(x.getSession().getId().equals(message.session().getId())){
                        x.getRealTimeData().result.setPoints(1);
                        x.getRealTimeData().result.setWinner(true);
                    }
                    else {
                        x.getRealTimeData().result.setPoints(0);
                        x.getRealTimeData().result.setWinner(false);
                    }
                    x.getSession().sendMessage(new TextMessage(wsMessageConverter.createFullMessage(TypesOfMessage.FINISH, 0, String.valueOf(number))));
                }
                endGame();
            }
        }
        else{
            for(var x : players.values()){
                x.getRealTimeData().result.setPoints(0);
                x.getRealTimeData().result.setWinner(false);
                x.getSession().sendMessage(new TextMessage(wsMessageConverter.createFullMessage(TypesOfMessage.FINISH, 0, String.valueOf(-1))));
            }
            endGame();
        }
    }

    @Override
    public void disconnect(Message message) {

    }

    private void endGame(){
        userService.saveUsers(players.values().stream().map(Player::getUser).collect(Collectors.toList()));
    }

    @Setter
    protected class XoPlayer{

        int number;
        boolean winner = false;
        Result result = new Result();

    }
    private record Finish(boolean winner, boolean may){}
    private  record Action(int number, int index){}
}
