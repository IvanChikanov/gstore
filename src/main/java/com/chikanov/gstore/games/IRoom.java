package com.chikanov.gstore.games;

import com.chikanov.gstore.records.ActionMessage;
import com.chikanov.gstore.records.CloseMessage;
import com.chikanov.gstore.records.ResultData;
import com.chikanov.gstore.records.WsPlayer;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public interface IRoom {

    boolean addUser(WsPlayer player) throws IOException;

    boolean readMessage(ActionMessage message) throws Exception;

    Set<ResultData> endGame();

    void sendMessage(WebSocketSession user, WebSocketMessage<?> message);

    boolean closeConnection(CloseMessage message);
}
