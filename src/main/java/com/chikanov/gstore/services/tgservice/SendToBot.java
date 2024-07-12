package com.chikanov.gstore.services.tgservice;

import com.chikanov.gstore.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SendToBot {

    @Value("${token.value}")
    private String token;

    @Autowired
    private GameService gameService;

    private void sendRequest(String url, String body){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        RestTemplate rest = new RestTemplate();
        rest.exchange(url, HttpMethod.POST, request, String.class);
    }

    private String createUrl(String typeOfQuery){
        return String.format("https://api.telegram.org/bot%s/%s", token, typeOfQuery);
    }


    public void sendInlineAnswer(String body){
        sendRequest(createUrl("answerInlineQuery"), body);
    }

    public void sendMessage(){
        sendRequest(createUrl("sendMessage"), "bbb");
    }
}
