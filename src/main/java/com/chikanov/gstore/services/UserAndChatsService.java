package com.chikanov.gstore.services;

import com.chikanov.gstore.entity.ChatEntity;
import com.chikanov.gstore.entity.ChatRoleKey;
import com.chikanov.gstore.entity.ChatRoles;
import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.repositories.ChatRoleRepository;
import com.chikanov.gstore.repositories.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserAndChatsService {

    private final ChatRoleRepository chatRoleRepository;
    private final UserRepository userRepository;

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
    }
}
