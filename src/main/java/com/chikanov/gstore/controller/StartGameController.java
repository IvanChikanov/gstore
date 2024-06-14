package com.chikanov.gstore.controller;

import com.chikanov.gstore.services.ReadJsonService;
import com.chikanov.gstore.services.UserAndChatsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;

@Controller
public class StartGameController {

    @Autowired
    private ReadJsonService readJsonService;
    @Autowired
    private UserAndChatsService userAndChatsService;

    @PostMapping("/bot")
    public ResponseEntity<?> sendBot(@RequestBody String postBody){
        try {
            System.out.println(postBody);
            readJsonService.read(postBody);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/miniapp")
    public String getGame(@RequestHeader("User-Game") String userGame){

        return "main";
    }

}
