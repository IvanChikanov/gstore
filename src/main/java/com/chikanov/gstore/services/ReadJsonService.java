package com.chikanov.gstore.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReadJsonService {

    @Value("${token.value}")
    private String token;

    public void read(String jsonValue) throws JsonProcessingException {
        System.out.println(jsonValue);
        ObjectMapper om = new ObjectMapper();
        JsonNode json = om.readTree(jsonValue);
        String url = String.format("https://api.telegram.org/bot%s/sendMessage", token);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = "";
        if(json.has("message") && json.get("message").has("text"))
        {
            if(json.get("message").get("text").asText().contains("/play"))
            {
                body = "{ \"chat_id\":" + json.get("message").get("chat").get("id").asText() + ", \"text\": \"test\", " +
                        "\"reply_markup\": " +
                            "{ \"inline_keyboard\": " +
                                "[[" +
                                    "{\"text\": \"play game\", \"url\": " +
                                        "\"https://t.me/Cooperation_chat_minigames_bot/coop_g_store\"" +
                                    "}" +
                                "]]" +
                            "}" +
                        "}";
            }
        } else if (json.has("my_chat_member")) {
            body = "{ \"chat_id\":" + json.get("my_chat_member").get("chat").get("id").asText() + ", \"text\": \"test\", " +
                    "\"reply_markup\": { \"inline_keyboard\": [[{\"text\": \"play game\", \"url\": " +
                    "\"https://chisch.ru/miniapp_controller\"}}]]}}";
        }
        if(!body.isEmpty())
        {
            HttpEntity<String> request = new HttpEntity<>(body, headers);
            RestTemplate rest = new RestTemplate();
            rest.exchange(url, HttpMethod.POST, request, String.class);
        }
    }
}
