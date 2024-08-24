package com.chikanov.gstore.games.objects;

import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.exceptions.WsException;
import com.chikanov.gstore.exceptions.enums.WsExceptionType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Getter
@Setter
public class Player<T>{
    private WebSocketSession session;
    private User user;
    private T realTimeData;
    private boolean isConnected;
    private boolean isActive = false;


    public void replaceSession(WebSocketSession newSession){
        session = newSession;
    }

    public void sendMessage(String message) throws WsException{
        try{
            session.sendMessage(new TextMessage(message));
        }
        catch (IOException ioException){
            throw new WsException("Сессия не найдена!", WsExceptionType.SESSION_ERROR);
        }
    }

}
