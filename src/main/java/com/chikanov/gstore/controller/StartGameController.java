package com.chikanov.gstore.controller;

import com.chikanov.gstore.services.ReadJsonService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.io.IOException;

@Controller
public class StartGameController {

    @Autowired
    private ReadJsonService readJsonService;

    @PostMapping("/miniapp_controller")
    public ResponseEntity<?> startGame(@RequestBody String postBody)
    {

        System.out.println(postBody);
        return ResponseEntity.ok(postBody);
    }
    @PostMapping("/bot_controller")
    public ResponseEntity<?> sendBot(@RequestBody String postBody){
        try {
            System.out.println(postBody);
            readJsonService.read(postBody);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/miniapp_controller")
    public String getGame(HttpServletRequest request) throws ServletException, IOException {
        return "main";
    }
    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody String body)
    {
        System.out.println(body);
        return ResponseEntity.status(200).build();
    }
}
