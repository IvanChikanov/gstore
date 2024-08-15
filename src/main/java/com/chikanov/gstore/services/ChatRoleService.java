package com.chikanov.gstore.services;

import com.chikanov.gstore.entity.ChatEntity;
import com.chikanov.gstore.entity.ChatRole;
import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.entity.dto.ChatDTO;
import com.chikanov.gstore.enums.Role;
import com.chikanov.gstore.repositories.ChatRepository;
import com.chikanov.gstore.repositories.ChatRoleRepository;
import com.chikanov.gstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoleService {
    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatRoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public ChatRole createChatRole(User user, ChatEntity chat, Role role)
    {
        ChatRole chatRole = new ChatRole();
        chatRole.setChat(chat);
        chatRole.setUser(user);
        chatRole.setRole(role);
        return chatRole;
    }
    public List<ChatDTO> getChats(User user, Role role){
        return roleRepository.getChats(user, role).stream().map(ch -> chatService.convertToDTO(ch)).toList();
    }


}
