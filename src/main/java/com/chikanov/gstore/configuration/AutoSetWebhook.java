package com.chikanov.gstore.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class AutoSetWebhook {
    @Value("${token.value}")
    private String TOKEN;
    private final String URL = "https://t.me/bot";
    private final String SET_WEBHOOK = "/setWebhook";
    private final Webhook webhook;
    public AutoSetWebhook()
    {
        webhook = new Webhook();
    }
    @EventListener(ApplicationReadyEvent.class)
    public void setWebhook() throws JsonProcessingException {
        try {
            RestTemplate rt = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ObjectMapper om = new ObjectMapper();
            HttpEntity<String> entity = new HttpEntity<>(om.writeValueAsString(webhook), headers);
            var response = rt.exchange(URL + TOKEN + SET_WEBHOOK, HttpMethod.POST, entity, String.class);
            System.out.println(response);
        }catch (JsonProcessingException ex){
            ex.printStackTrace();
        }

    }
    private class Webhook
    {
        final String url = "https://chisch.ru/bot_controller";
        final String allowed_updates = "[\"inline_query\", \"my_chat_member\", \"callback_query\", \"chosen_inline_result\"]";
        final String secret_token;
        Webhook()
        {
            this.secret_token = UUID.randomUUID().toString();
        }

    }
}
