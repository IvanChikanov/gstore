package com.chikanov.gstore.websock.service;

import com.chikanov.gstore.enums.TypesOfMessage;
import com.chikanov.gstore.records.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class WsMessageConverter {

    @Autowired
    private ObjectMapper objectMapper;

    public Message createMessage(WebSocketSession session, String message)
    {
        String[] cutPayload = message.split(":::");
        String[] types = cutPayload[0].split(":");
        return new Message(TypesOfMessage.valueOf(types[0]), Integer.parseInt(types[1]), cutPayload[1], session);
    }

    public String createFullMessage(TypesOfMessage t, int n, String p) {
        return String.format("%s:%d:::%s", t, n, p);
    }
}
