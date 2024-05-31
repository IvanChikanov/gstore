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
            body = "{ \"chat_id\":" + json.get("message").get("chat").get("id").asText() + ", \"text\": \"Ну что же, мне кажется ты желаещь сыгрть в игру?\", " +
                    "\"reply_markup\": " +
                    "{ \"inline_keyboard\": " +
                    "[[" +
                    "{\"text\": \"Играть!\", \"url\": " +
                    "\"https://t.me/Cooperation_chat_minigames_bot/coop_g_store\"" +
                    "}" +
                    "]]" +
                    "}" +
                    "}";
            send();
        }
    }
}
