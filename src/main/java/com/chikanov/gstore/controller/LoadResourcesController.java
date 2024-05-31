package com.chikanov.gstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inside")
public class LoadResourcesController {
    @PostMapping("/main")
    public ResponseEntity<?> check(@RequestBody String body)
    {
        System.out.println(body);
        return ResponseEntity.status(200).build();
    }
}
