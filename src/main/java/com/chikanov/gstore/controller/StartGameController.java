package com.chikanov.gstore.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

@Slf4j
@Controller
public class StartGameController {
    @Value("${token.value}")
    private String token;

    @PostMapping("/miniapp_controller")
    public ResponseEntity<?> startGame(@RequestBody String postBody)
    {

        System.out.println(postBody);
        return ResponseEntity.ok(postBody);
    }
    @PostMapping("/bot_controller")
    public ResponseEntity<?> sendBot(@RequestBody String postBody) throws IOException {
        System.out.println(postBody);
        ObjectMapper om = new ObjectMapper();
        JsonNode json = om.readTree(postBody.getBytes(StandardCharsets.UTF_8));
        if(json.get("message").get("text").asText().contains("/play")) {
            String url = String.format("https://api.telegram.org/bot%s/sendMessage", token);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String body = "{ \"chat_id\":" + json.get("message").get("chat").get("id").asText() + ", \"text\": \"test\", " +
                    "\"reply_markup\": { \"inline_keyboard\": [[{\"text\": \"play game\", \"web_app\": " +
                    "{\"url\": \"https://chisch.ru/miniapp_controller\"}}]]}}";
            System.out.println(body);
            HttpEntity<String> request = new HttpEntity<>(body, headers);
            RestTemplate rest = new RestTemplate();
            rest.exchange(url, HttpMethod.POST, request, String.class);
            return ResponseEntity.ok(postBody);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @GetMapping("/miniapp_controller")
    public ResponseEntity<?> getGame(HttpServletRequest request) throws ServletException, IOException {
        String r = "<head><script src=\"https://telegram.org/js/telegram-web-app.js\"></script></head>" +
                "<body style='color: white;'></body>" +
                "<script>" +
                "let data = window.Telegram.WebApp.initDataUnsafe;" +
                "try{" +
                "document.body.innerHTML = 'Hello, ' + data.user.username;" +
                "}catch(exept){document.body.innerHTML = exept;}" +
                "</script>";
        return ResponseEntity.ok(r);
    }
}
