package com.chikanov.gstore.services;

import com.chikanov.gstore.configuration.AutoSetWebhook;
import com.chikanov.gstore.entity.ChatEntity;
import com.chikanov.gstore.entity.ChatRoleKey;
import com.chikanov.gstore.entity.ChatRoles;
import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.entity.tgentities.InlineAnswer;
import com.chikanov.gstore.entity.tgentities.TgQueries;
import com.chikanov.gstore.enums.Role;
import com.chikanov.gstore.enums.TelegramUpdates;
import com.chikanov.gstore.services.tgservice.SendToBot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReadJsonService {

    @Autowired
    private SendMessageService sendMessageService;
    @Autowired
    private UserAndChatsService userAndChatsService;

    @Autowired
    private SendToBot send;


    public void read(String jsonValue) throws JsonProcessingException {
        System.out.println(jsonValue);
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TgQueries query = om.readValue(jsonValue, TgQueries.class);
        if(query.getInlineQuery() != null)
        {
            InlineAnswer answer = new InlineAnswer();
            answer.setInlineQueryId(query.getInlineQuery().getId());
            answer.getButton().setText("Play!");
            answer.getButton().getWebApp().setURL("https://chisch.ru/miniapp");
            send.sendInlineAnswer(om.writeValueAsString(answer));
        }
        switchType(om.readTree(jsonValue));
    }
    private void switchType(JsonNode json)
    {
        Optional<TelegramUpdates> update = Arrays.stream(TelegramUpdates.values()).
                filter(tu-> json.has(tu.getStr())).findFirst();
        update.ifPresent(upd->{
            switch (upd)
            {
                case MY_CHAT_MEMBER -> chat(json);
            }
        });
    }
    private void message(JsonNode json){
        if(json.get("message").has("text") && json.get("message").get("text").asText().contains("/play"))
        {
            sendMessageService.send(json.get("message").get("chat").get("id").asLong(),
                    "Желаешь сыграть в игру ?\nДавай братишка, жми кнопку Играть!");
        }
    }
    private void chat(JsonNode jsonNode){
        JsonNode json = jsonNode.get("my_chat_member");
        if(json.get("new_chat_member").get("user").get("id").asText().equals(AutoSetWebhook.getBOT()))
        {
            String status = json.get("new_chat_member").get("status").asText();
            if(status.equals("member"))
            {
                User user = userAndChatsService.loadUser(json.get("from").get("id").asText());
                ChatEntity chat = userAndChatsService.createChat(json.get("chat"));
                ChatRoles chatRoles = new ChatRoles();
                chatRoles.setChat(chat);
                chatRoles.setUser(user);
                chatRoles.setRole(Role.ADMIN);
                userAndChatsService.saveChatRole(chatRoles);
                sendMessageService.send(json.get("chat").get("id").asLong(),
                        "Желаешь сыграть в игру ?\nДавай братишка, жми кнопку Играть!");
            }
            else
            {
                ChatRoleKey chatRoleKey = new ChatRoleKey();
                chatRoleKey.setUser(json.get("from").get("id").asText());
                chatRoleKey.setChat(json.get("chat").get("id").asLong());
                userAndChatsService.deleteChatRole(chatRoleKey);
            }
        }
    }
}
