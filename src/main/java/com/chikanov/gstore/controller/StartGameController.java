package com.chikanov.gstore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class StartGameController {
    @PostMapping("/miniapp_controller")
    public ResponseEntity<?> startGame(@RequestBody String postBody)
    {
        System.out.println(postBody);
        return ResponseEntity.ok(postBody);
    }
    @GetMapping("/miniapp_controller")
    public ResponseEntity<?> getGame()
    {
        return ResponseEntity.ok("hello");
    }
}
