package com.chikanov.gstore.exceptions;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WsExceptionHandler extends Exception{
    public WsExceptionHandler(WebSocketSession session, CloseStatus status){
        super("Произошла ошибка в WebSocket");

    }
    public WsExceptionHandler(WebSocketSession session, String message){
        super(message);
        try(WebSocketSession ws = session){
            ws.close(CloseStatus.NO_STATUS_CODE);
        }
        catch (IOException ex){

        }
    }
}
