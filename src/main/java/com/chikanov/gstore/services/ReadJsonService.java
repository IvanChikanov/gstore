package com.chikanov.gstore.services;

import com.chikanov.gstore.configuration.AutoSetWebhook;
import com.chikanov.gstore.entity.tgentities.*;
import com.chikanov.gstore.services.tgservice.MessageService;
import com.chikanov.gstore.services.tgservice.SendToBot;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReadJsonService {

    @Autowired
    private SendMessageService sendMessageService;

    @Autowired
    private SendToBot send;

    @Autowired
    private MessageService messageService;

    private ObjectMapper om;

    public ReadJsonService()
    {
        om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
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
        ChatMember newMember = myChatMember.getNewMember();
        if(newMember.getUser().getId().equals(AutoSetWebhook.getBOT())){
            if(newMember.getStatus().equals("member"))
            {
                sendMessageService.send(om.writeValueAsString(messageService.oneButtonMessage(
                        "Давай знакомится!\nЯ бот, в котором есть различные игры. " +
                                "Добавляй меня в чаты и вы сможете сорвеноваться и получать статистику побед в рамках конкретного чата!\n" +
                                "Так же есть и одиночные игры!\nПриятной игры!",
                        myChatMember.getChat().getId(),
                        messageService.oneButtonKeyboard(
                                "Давай играть!",
                                "https://chisch.ru/miniapp?startapp=private"
                        )
                )));
            }
        }
    }

}
