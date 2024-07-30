package com.chikanov.gstore.services.tgservice;

import com.chikanov.gstore.entity.ChatEntity;
import com.chikanov.gstore.entity.ChatRole;
import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.enums.Role;
import com.chikanov.gstore.repositories.ChatRepository;
import com.chikanov.gstore.repositories.ChatRoleRepository;
import com.chikanov.gstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRoleService {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatRoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public void createChatRole(User user, ChatEntity chat, Role role)
    {
        ChatRole chatRole = new ChatRole();
        chatRole.setChat(chat);
        chatRole.setUser(user);
        chatRole.setRole(role);
        roleRepository.save(chatRole);
    }
}
