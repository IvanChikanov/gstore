package com.chikanov.gstore.controller;

import com.chikanov.gstore.services.UserAndChatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inside")
public class LoadResourcesController {
    @Autowired
    private UserAndChatsService userAndChatsService;
    @PostMapping("/startapp")
    public ResponseEntity<?> check(@RequestBody String body)
    {
        System.out.println(body);
        //userAndChatsService.connectUserToGame(userGame);
        return ResponseEntity.status(200).build();
    }
}
