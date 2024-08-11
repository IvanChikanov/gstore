package com.chikanov.gstore.exceptions;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

public class WsExceptionHandler extends Exception{
    public WsExceptionHandler(WebSocketSession session, CloseStatus status){
        super("Произошла ошибка в WebSocket");

    }
    private void switchError(CloseStatus closeStatus){
        switch (closeStatus.getCode()){
            default -> {}
        }
    }
}
