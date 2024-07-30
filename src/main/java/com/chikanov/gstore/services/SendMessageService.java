package com.chikanov.gstore.services;

import com.chikanov.gstore.entity.tgentities.TgMessage;
import com.chikanov.gstore.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class SendMessageService {
    @Value("${token.value}")
    private String token;

    @Autowired
    private GameService gameService;

    public void send(String tgMessageJson)
    {
        String url = String.format("https://api.telegram.org/bot%s/sendMessage", token);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(tgMessageJson, headers);
        RestTemplate rest = new RestTemplate();
        rest.exchange(url, HttpMethod.POST, request, String.class);
    }
//    private String createBody(Long chatId, String text)
//    {
//        TgMessage message = new TgMessage();
//        message.setChatId(chatId);
//        message.
////        return "{ \"chat_id\":" + chatId + ", \"text\": \"" + text + "\", \"reply_markup\": " +
////                    "{ \"inline_keyboard\": " +
////                        "[[" +
////                            "{\"text\": \"Играть!\", \"url\": " +
////                            "\"https://t.me/Cooperation_chat_minigames_bot/coop_g_store?startapp=" +
////                                gameService.createGame(chatId) + "\"" +
////                            "}" +
////                        "]]" +
////                    "}" +
////                "}";
//    }
}
