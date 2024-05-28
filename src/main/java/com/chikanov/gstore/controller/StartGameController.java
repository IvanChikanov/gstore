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
        String url = String.format("https://api.telegram.org/bot%s/sendMessage", token);
        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper om = new ObjectMapper();
        JsonNode json = om.readTree(postBody.getBytes(StandardCharsets.UTF_8));
        System.out.println();
        System.out.println(json.get("chat").asText());
        System.out.println();
        System.out.println(url);
        System.out.println();
        String body = "{ \"chat_id\":" + json.get("chat").get("id").asText() + ", \"text\": \"test\"}";
        System.out.println(body);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        RestTemplate rest = new RestTemplate();
        rest.exchange(url, HttpMethod.POST, request, String.class);
        return ResponseEntity.ok(postBody);
    }
    @GetMapping("/miniapp_controller")
    public ResponseEntity<?> getGame(HttpServletRequest request) throws ServletException, IOException {
        System.out.println(request.getQueryString());
        System.out.println(request.getRequestURI());
        System.out.println(request.getRequestURL());

        var iter = request.getHeaderNames().asIterator();
        while(iter.hasNext()){
            String s = iter.next();
            System.out.println(s);
            System.out.println(request.getHeader(s));

        }
        return ResponseEntity.ok("hello");
    }
}
