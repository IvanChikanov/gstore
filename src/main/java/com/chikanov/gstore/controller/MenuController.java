package com.chikanov.gstore.controller;

import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.entity.dto.GameTypeFilteredDTO;
import com.chikanov.gstore.entity.tgentities.TgUser;
import com.chikanov.gstore.services.GameTypeService;
import com.chikanov.gstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/inside")
public class MenuController extends AbstractController{


    @Autowired
    UserService userService;

    @Autowired
    GameTypeService gameTypeService;

    @GetMapping("/private")
    public ResponseEntity<GameTypeFilteredDTO> getGames(@RequestAttribute("user") TgUser user) throws Exception {
        User insideUser = userService.getOrCreate(user);
        return ResponseEntity.ok(gameTypeService.getActiveGames());
    }

    @GetMapping("/chats")
    public ResponseEntity<?> getAdminChats() throws Exception
    {
        return ResponseEntity.ok("");
    }

    @GetMapping("/send_game")
    public ResponseEntity<?> sendGame() throws Exception
    {
        return ResponseEntity.ok("");
    }

    @GetMapping("/load_module/{uuid}")
    public ResponseEntity<?> loadModule(@PathVariable("uuid") UUID gameId) throws Exception
    {
        return ResponseEntity.ok("");
    }


}
