package com.chikanov.gstore.controller;

import com.chikanov.gstore.entity.ChatEntity;
import com.chikanov.gstore.entity.dto.ChatDTO;
import com.chikanov.gstore.entity.dto.GameTypeFilteredDTO;
import com.chikanov.gstore.entity.dto.SendGameDTO;
import com.chikanov.gstore.entity.tgentities.TgUser;
import com.chikanov.gstore.enums.Role;
import com.chikanov.gstore.services.ChatRoleService;
import com.chikanov.gstore.services.GameTypeService;
import com.chikanov.gstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/inside")
public class MenuController extends AbstractController{


    @Autowired
    private UserService userService;

    @Autowired
    private GameTypeService gameTypeService;

    @Autowired
    private ChatRoleService chatRoleService;

    @GetMapping("/private")
    public ResponseEntity<GameTypeFilteredDTO> getGames() throws Exception {
        return ResponseEntity.ok(gameTypeService.getActiveGames());
    }

    @Transactional
    @GetMapping("/chats")
    public ResponseEntity<List<ChatDTO>> getAdminChats(@RequestAttribute("user") TgUser user) throws Exception
    {
        return ResponseEntity.ok(chatRoleService.getChats(userService.getOrCreate(user), Role.ADMIN));
    }

    @PostMapping("/send_game")
    public ResponseEntity<?> sendGame(@RequestBody SendGameDTO sendGameDTO) throws Exception
    {
        System.out.println(sendGameDTO.game_id());
        return ResponseEntity.ok(UUID.randomUUID());
    }

    @GetMapping("/load_module/{uuid}")
    public ResponseEntity<?> loadModule(@PathVariable("uuid") UUID gameId) throws Exception
    {
        return ResponseEntity.ok("");
    }


}
