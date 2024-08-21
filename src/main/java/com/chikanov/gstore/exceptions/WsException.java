package com.chikanov.gstore.exceptions;

import com.chikanov.gstore.exceptions.enums.WsExceptionType;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WsException extends Exception{
    public final WsExceptionType status;

    public WsException(String message,  WsExceptionType type){
        super(message);
        this.status = type;
    }

}
