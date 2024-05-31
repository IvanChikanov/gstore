package com.chikanov.gstore.services;

import com.chikanov.gstore.enums.TelegramUpdates;
import com.chikanov.gstore.services.messagehandling.Message;
import com.chikanov.gstore.services.messagehandling.Chat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Optional;

@Service
public class ReadJsonService {
    @Autowired
    SendMessageService sendMessageService;

    @Value("${token.value}")
    private String token;

    public void read(String jsonValue) throws JsonProcessingException {
        System.out.println(jsonValue);
        ObjectMapper om = new ObjectMapper();
        switchType(om.readTree(jsonValue));
    }
    private void switchType(JsonNode json)
    {
        Optional<TelegramUpdates> update = Arrays.stream(TelegramUpdates.values()).
                filter(tu-> json.has(tu.getStr())).findFirst();
        update.ifPresent(upd->{
            System.out.println(upd.getStr());
            switch (upd)
            {
                case MESSAGE -> new Message(json, sendMessageService);
                case MY_CHAT_MEMBER -> new Chat();
            }
        });
    }
}
