package com.chikanov.gstore.services.messagehandling;

import com.chikanov.gstore.services.SendMessageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Chat extends AbstractMessage{
    public Chat(JsonNode json, SendMessageService sendMessageService)
    {
        super(sendMessageService);
        this.json = json;
        ObjectMapper om = new ObjectMapper();
        try {
            System.out.println(om.writeValueAsString(json));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
