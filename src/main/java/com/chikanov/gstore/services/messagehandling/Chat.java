package com.chikanov.gstore.services.messagehandling;

import com.chikanov.gstore.configuration.AutoSetWebhook;
import com.chikanov.gstore.services.SendMessageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Chat extends AbstractMessage{
    public Chat(JsonNode json, SendMessageService sendMessageService)
    {
        super(sendMessageService);
        this.json = json;
    }
    private void handling()
    {
        try {
            ObjectMapper om = new ObjectMapper();
            if(json.get("my_chat_member").get("new_chat_member").get("id").asText().equals(AutoSetWebhook.getBOT()))
            {

            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
