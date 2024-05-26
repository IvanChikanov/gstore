package com.chikanov.gstore.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Iterator;

@Controller
public class StartGameController {
    @PostMapping("/miniapp_controller")
    public ResponseEntity<?> startGame(@RequestBody String postBody)
    {

        System.out.println(postBody);
        return ResponseEntity.ok(postBody);
    }
    @PostMapping("/bot_controller")
    public ResponseEntity<?> sendBot(@RequestBody String postBody)
    {
        System.out.println(postBody);
        return ResponseEntity.ok(postBody);
    }
    @GetMapping("/miniapp_controller")
    public ResponseEntity<?> getGame(HttpServletRequest request)
    {
        System.out.println(request.getQueryString());
        System.out.println(request.getRequestURI());
        var str = request.getHeaderNames().asIterator();
        while(str.hasNext())
        {
            System.out.println(str.next());
            System.out.println();
        }

        return ResponseEntity.ok("hello");
    }
}
