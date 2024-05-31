package com.chikanov.gstore.services.messagehandling;

import com.chikanov.gstore.services.SendMessageService;
import com.fasterxml.jackson.databind.JsonNode;

abstract class AbstractMessage {
    private SendMessageService sendMessageService;
    protected AbstractMessage(SendMessageService sendMessageService)
    {
        this.sendMessageService = sendMessageService;
    }
    protected JsonNode json;
    protected String body;

    protected void send(String chatId, String text)
    {
        sendMessageService.send(chatId, text);
    }
}
