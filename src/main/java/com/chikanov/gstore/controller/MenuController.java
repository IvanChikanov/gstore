package com.chikanov.gstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/inside")
public class MenuController extends AbstractController{

    @GetMapping("/private")
    public ResponseEntity<?> getGames()
    {
        return ResponseEntity.ok("");
    }

    @GetMapping("/chats")
    public ResponseEntity<?> getAdminChats()
    {
        return ResponseEntity.ok("");
    }

    @PostMapping("/send_game")
    public ResponseEntity<?> sendGame()
    {
        return ResponseEntity.ok("");
    }

    @GetMapping("/load_module/{uuid}")
    public ResponseEntity<?> loadModule(@PathVariable("uuid") UUID gameId)
    {
        return ResponseEntity.ok("");
    }


}
