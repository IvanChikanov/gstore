package com.chikanov.gstore.services;

import com.chikanov.gstore.components.MessageTextKeeper;
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

    @Autowired
    private MessageTextKeeper messageTextKeeper;

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
                if(myChatMember.getChat().getType().equals("private"))
                    sendMessageService.send(om.writeValueAsString(messageService.oneButtonMessage(
                            messageTextKeeper.ru.get("private_hello"),
                            myChatMember.getChat().getId(),
                            messageService.oneButtonKeyboard(
                                    "Давай играть!",
                                    "https://t.me/Cooperation_chat_minigames_bot/coop_g_store?startapp="
                            )
                    )));
                else
                    sendMessageService.send(om.writeValueAsString(messageService.oneButtonMessage(
                            messageTextKeeper.ru.get("group_hello"),
                            myChatMember.getChat().getId(),
                            messageService.oneButtonKeyboard(
                                    "Начать личный чат!",
                                    "https://t.me/Cooperation_chat_minigames_bot"
                            )
                    )));
            }
        }
    }

}
