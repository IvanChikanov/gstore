package com.chikanov.gstore.services.messagehandling;

import com.chikanov.gstore.services.SendMessageService;
import com.fasterxml.jackson.databind.JsonNode;

public class Message extends AbstractMessage{
    public Message(JsonNode node, SendMessageService sendMessageService)
    {
        super(sendMessageService);
        json = node;
        handling();
    }
    private void handling()
    {
        if(json.get("message").has("text") && json.get("message").get("text").asText().contains("/play"))
        {
            send(json.get("message").get("chat").get("id").asText(),
                    "Желаешь сыграть в игру ?\nДавай братишка, жми кнопку ```Играть!```");
        }
    }
}
