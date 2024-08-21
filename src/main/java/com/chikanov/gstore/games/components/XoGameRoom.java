package com.chikanov.gstore.games.components;

import com.chikanov.gstore.entity.Game;
import com.chikanov.gstore.entity.Result;
import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.enums.TypesOfMessage;
import com.chikanov.gstore.exceptions.WsException;
import com.chikanov.gstore.exceptions.enums.WsExceptionType;
import com.chikanov.gstore.games.objects.Player;
import com.chikanov.gstore.records.*;
import com.chikanov.gstore.repositories.ResultRepository;
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
    private ResultRepository resultRepository;

    private final Integer[] symbol = new Integer[]{1, 2};
    private final int size;
    private final int[] cells;
    private List<Line> lines;

    private final ObjectMapper objectMapper = new ObjectMapper();
    public XoGameRoom(Game g){
        super(g);
        size = 3;
        max = 2;
        cells = new int[size * size];
        lines = filler(size);
        Arrays.fill(cells, 0);
    }


    private void startGame(int num) throws WsException{
        int count = 0;
        int randomNumber = 0;

        for(var player : players.values()){

            count++;
            randomNumber = count < 2 ?
                new Random().nextInt(1, 3):
                randomNumber == 1 ? 2 : 1;
            player.getRealTimeData().number = randomNumber;
            player.sendMessage(
                    wsMessageConverter.createFullMessage(
                            TypesOfMessage.START, 0,
                            String.valueOf(randomNumber)
                    )
            );
        }
    }
    private void sendAllBut(String id, String message) throws WsException
    {
        for(var key: players.keySet()){
            if(!key.equals(id)){
                players.get(key).sendMessage(message);
            }
        }
    }

    private List<Line> filler(int size)
    {
        List<Integer[]> lines = new ArrayList<>();
        Integer[] right = new Integer[size];
        Integer[] left = new Integer[size];
        for (int col = 0; col < size; col++) {
            Integer[] rows = new Integer[size];
            Integer[] cols = new Integer[size];
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
        return new ArrayList<>(lines.stream().map(l-> new Line(Arrays.stream(l).toList())).toList());
    }
    private Finish checkResults(int index, int number){
        List<Line> l = lines.stream().filter(li -> li.contains(index)).toList();
        Finish f = null;
        List<Line> toDel = new ArrayList<>();
        for(Line line: l){
            int r = line.check(number);
            if(r == number) {
                f = new Finish(true, true);
                break;
            }
            else {
                if(r == -1)
                    toDel.add(line);
                else
                    f = new Finish(false, true);
            }
        }
        lines.removeAll(toDel);
        if(f == null){
            f = lines.isEmpty() ? new Finish(false, false) : new Finish(false, true);
        }
        return f;
    }

    @Override
    public void addUser(User user, WebSocketSession session) throws WsException {
            if (players.size() < max) {
                Player<XoPlayer> player = new Player<>();
                Result result = new Result();
                result.setGame(game);
                result.setUser(user);
                player.setUser(user);
                player.setSession(session);
                player.setActive(true);
                player.setRealTimeData(new XoPlayer());
                player.getRealTimeData().setResult(result);
                players.put(session.getId(), player);
                if (players.size() == max)
                    startGame(0);
            } else {
                Optional<Player<XoPlayer>> us = players.values().stream().filter(p -> p.getUser().getId().equals(user.getId())).findFirst();
                us.orElseThrow(()-> new WsException("Комната уже полна игроков!", WsExceptionType.ROOM_OVERLOAD)).replaceSession(session);
                startGame(1);
            }
    }

    @Override
    public void action(Message message) throws WsException{
        int index = Integer.parseInt(message.payload());
        int number = players.get(message.session().getId()).getRealTimeData().number;
        sendAllBut(message.session().getId(),
                wsMessageConverter.createFullMessage(TypesOfMessage.ACTION, number, String.valueOf(index)));
        if(index >= 0) {
            cells[index] = number;
            Finish result = checkResults(index, number);
            if (result.may()) {
                if (result.winner()) {
                    for (var x : players.values()) {
                        if (x.getSession().getId().equals(message.session().getId())) {
                            x.getRealTimeData().result.setPoints(1);
                            x.getRealTimeData().result.setWinner(true);
                        } else {
                            x.getRealTimeData().result.setPoints(0);
                            x.getRealTimeData().result.setWinner(false);
                        }
                        x.sendMessage(wsMessageConverter.createFullMessage(TypesOfMessage.FINISH, 0, String.valueOf(number)));
                    }
                    endGame();
                }
            } else {
                for (var x : players.values()) {
                    x.getRealTimeData().result.setPoints(0);
                    x.getRealTimeData().result.setWinner(false);
                    x.sendMessage(wsMessageConverter.createFullMessage(TypesOfMessage.FINISH, 0, String.valueOf(-1)));
                }
                endGame();
            }
        }
    }

    @Override
    public void disconnect(WebSocketSession session) throws WsException{
        players.get(session.getId()).setActive(false);
        sendAllBut(session.getId(), wsMessageConverter.createFullMessage(TypesOfMessage.ERROR, 0, "Противник отключился, ждем..."));
    }

    private void endGame(){
        List<Result> res = players.values().stream().map(p-> p.getRealTimeData().result).toList();
        resultRepository.saveAll(res);
        eventPublisher.publishEvent(game);
    }

    @Setter
    protected class XoPlayer{

        int number;
        boolean winner = false;
        Result result = new Result();

    }
    private record Finish(boolean winner, boolean may){}

    private class Line{
        List<Integer> is;
        Line(List<Integer> is){
            this.is = is;
        }
        boolean contains(int i){
            return  is.contains(i);
        }
        int check(int num){
            int r = num;
            for(var i : is){
               if(cells[i] != num){
                   r = cells[i] != 0 ? -1 : 0;
                   break;
               }
            }
            return r;
        }
    }
}
