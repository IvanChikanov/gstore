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

    public void send(String body)
    {
        String url = String.format("https://api.telegram.org/bot%s/sendMessage", token);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        RestTemplate rest = new RestTemplate();
        rest.exchange(url, HttpMethod.POST, request, String.class);
    }
}
