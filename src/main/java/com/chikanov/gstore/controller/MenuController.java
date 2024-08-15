package com.chikanov.gstore.controller;

import com.chikanov.gstore.entity.ChatEntity;
import com.chikanov.gstore.entity.dto.ChatDTO;
import com.chikanov.gstore.entity.dto.GameTypeFilteredDTO;
import com.chikanov.gstore.entity.dto.SendGameDTO;
import com.chikanov.gstore.entity.tgentities.TgUser;
import com.chikanov.gstore.enums.Role;
import com.chikanov.gstore.services.*;
import com.chikanov.gstore.services.tgservice.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private SendMessageService sendMessageService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private GameService gameService;

    @Autowired
    private ObjectMapper om;

    @GetMapping("/private")
    public ResponseEntity<GameTypeFilteredDTO> getGames() throws Exception {
        return ResponseEntity.ok(gameTypeService.getActiveGames());
    }

    @Transactional
    @GetMapping("/chats")
    public ResponseEntity<List<ChatDTO>> getAdminChats(@RequestAttribute("user") TgUser user) throws Exception
    {
        return ResponseEntity.ok(chatRoleService.getChats(userService.getById(user.getId()), Role.ADMIN));
    }

    @PostMapping("/send_game")
    public ResponseEntity<String> sendGame(@RequestAttribute("user") TgUser user, @RequestBody SendGameDTO sendGameDTO) throws Exception
    {
        String id = gameService.createGame(sendGameDTO.chat_id(), sendGameDTO.game_id());
        sendMessageService.send(om.writeValueAsString(messageService.oneButtonMessage(
                String.format("@%s приглашает вас в игру", user.getUsername()),
                sendGameDTO.chat_id(),
                messageService.oneButtonKeyboard(
                        "Играть",
                        String.format("https://t.me/Cooperation_chat_minigames_bot/coop_g_store?startapp=%s",id))
                        )
                )
        );
        return ResponseEntity.ok(id);
    }

    @GetMapping("/load_module/{uuid}")
    public ResponseEntity<String> loadModule(@PathVariable("uuid") UUID gameId) throws Exception
    {
        return ResponseEntity.ok(gameService.getGameModule(gameId));
    }


}
