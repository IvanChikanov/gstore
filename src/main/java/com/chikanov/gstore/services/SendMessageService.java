package com.chikanov.gstore.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SendMessageService {
    @Value("${token.value}")
    private String token;

    public void send(String chatId, String text)
    {
        String url = String.format("https://api.telegram.org/bot%s/sendMessage", token);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(createBody(chatId, text), headers);
        RestTemplate rest = new RestTemplate();
        rest.exchange(url, HttpMethod.POST, request, String.class);
    }
    private String createBody(String chatId, String text)
    {
        return "{ \"chat_id\":" + chatId + ", \"text\": \"" + text + "\", \"reply_markup\": " +
                    "{ \"inline_keyboard\": " +
                        "[[" +
                            "{\"text\": \"Play!\", \"url\": " +
                            "\"https://t.me/Cooperation_chat_minigames_bot/coop_g_store\"" +
                            "}" +
                        "]]" +
                    "}" +
                "}";
    }
}
