package com.chikanov.gstore.services;

import com.chikanov.gstore.entity.*;
import com.chikanov.gstore.enums.Role;
import com.chikanov.gstore.repositories.ChatRepository;
import com.chikanov.gstore.repositories.ChatRoleRepository;
import com.chikanov.gstore.repositories.GameRepository;
import com.chikanov.gstore.repositories.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class UserAndChatsService {

    private final ChatRoleRepository chatRoleRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final ChatRepository chatRepository;

    public User loadUser(String id)
    {
        return userRepository.findById(id).orElse(createUser(id));
    }
    public User createUser(String id)
    {
        User user = new User();
        user.setPremium(false);
        user.setId(id);
        return user;
    }
    public ChatEntity createChat(JsonNode jsonChat)
    {
        ChatEntity chat = new ChatEntity();
        chat.setChat_id(jsonChat.get("id").asLong());
        chat.setName(jsonChat.get("title").asText());
        return chat;
    }
    public void saveChatRole(ChatRoles chatRoles)
    {
        chatRoleRepository.save(chatRoles);
    }
    public void deleteChatRole(ChatRoleKey chatRoleKey)
    {
        chatRoleRepository.deleteById(chatRoleKey);
    }
    public void connectUserToGame(String userGame)
    {
        String[] split = userGame.split("&");
        Optional<Game> gameOptional = gameRepository.findById(UUID.fromString(split[1]));
        if(gameOptional.isPresent())
        {
            User user = loadUser(split[0]);
            Game game = gameOptional.get();
            ChatRoleKey chatRolekey = new ChatRoleKey();
            chatRolekey.setChat(game.getChatId().getChat_id());
            chatRolekey.setUser(user.getId());
            Optional<ChatRoles> chatRoles = chatRoleRepository.findById(chatRolekey);
            if(chatRoles.isEmpty()){
                ChatRoles chRo = new ChatRoles();
                chRo.setRole(Role.USER);
                chRo.setUser(user);
                chRo.setChat(game.getChatId());
                chatRoleRepository.save(chRo);
            }
        }
        else
        {
            throw new RuntimeException("Игра не существует!");
        }

    }
}
