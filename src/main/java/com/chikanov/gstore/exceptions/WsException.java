package com.chikanov.gstore.exceptions;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WsException extends Exception{
    public final CloseStatus status;

    public WsException(String message, CloseStatus status){
        super(message);
        this.status = status;
    }

}
