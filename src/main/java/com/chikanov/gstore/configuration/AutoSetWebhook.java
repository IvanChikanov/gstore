package com.chikanov.gstore.configuration;

import com.chikanov.gstore.enums.TelegramUpdates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AutoSetWebhook {
    @Value("${token.value}")
    private String TOKEN;
    private final String URL = "https://api.telegram.org/bot";
    private final String SET_WEBHOOK = "/setWebhook";
    private final Webhook webhook;
    public AutoSetWebhook()
    {
        webhook = new Webhook();
    }
    @EventListener(ApplicationReadyEvent.class)
    public void setWebhook() {
        try {
            RestTemplate rt = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ObjectMapper om = new ObjectMapper();
            HttpEntity<String> entity = new HttpEntity<>(om.writeValueAsString(webhook), headers);
            rt.exchange(URL + TOKEN + SET_WEBHOOK, HttpMethod.POST, entity, String.class);
        }catch (JsonProcessingException ex){
            ex.printStackTrace();
        }

    }
    public String getWebhookToken()
    {
        return webhook.getSecret_token();
    }
    @Getter
    @Setter
    private class Webhook
    {
        final String url = "https://chisch.ru/bot";
        final String allowed_updates = "[\"" + Arrays.stream(TelegramUpdates.values()).
                map(tu-> tu.getStr()).
                collect(Collectors.joining("\", \"")) + "\"]";
        final String secret_token;
        Webhook()
        {
            this.secret_token = UUID.randomUUID().toString();
        }

    }
}
