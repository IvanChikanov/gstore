package com.chikanov.gstore.services;

import com.chikanov.gstore.configuration.AutoSetWebhook;
import com.chikanov.gstore.entity.ChatEntity;
import com.chikanov.gstore.entity.ChatRoles;
import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.entity.tgentities.InlineAnswer;
import com.chikanov.gstore.entity.tgentities.InlineQuery;
import com.chikanov.gstore.entity.tgentities.MyChatMember;
import com.chikanov.gstore.entity.tgentities.TgQueries;
import com.chikanov.gstore.enums.Role;
import com.chikanov.gstore.enums.TelegramUpdates;
import com.chikanov.gstore.services.tgservice.SendToBot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class ReadJsonService {

    @Autowired
    private SendMessageService sendMessageService;

    @Autowired
    private SendToBot send;

    private ObjectMapper om;

    public ReadJsonService()
    {
        om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    public void read(String jsonValue) throws JsonProcessingException {
        System.out.println(jsonValue);
        TgQueries query = om.readValue(jsonValue, TgQueries.class);
        System.out.println(query.getUpdate());
        System.out.println(query.getInlineQuery());
        System.out.println(query.getType());
        switchType(query);
    }
    private void switchType(TgQueries query) throws JsonProcessingException
    {
       switch (query.getType()){
           case "inline" -> handleInline(query.getInlineQuery());
           case "chatMember" -> handleChatMember(query.getMyChatMember());
       }
    }

    private void handleInline(InlineQuery inlineQuery) throws JsonProcessingException
    {
        System.out.println(inlineQuery.getId());
        InlineAnswer answer = new InlineAnswer();
        answer.setInlineQueryId(inlineQuery.getId());
        answer.getButton().setText("Play!");
        answer.getButton().getWebApp().setURL("https://chisch.ru/miniapp?startapp=private");
        send.sendInlineAnswer(om.writeValueAsString(answer));
    }

    private void handleChatMember(MyChatMember myChatMember) throws JsonProcessingException{

    }

}
