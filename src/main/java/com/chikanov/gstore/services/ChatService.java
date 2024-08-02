package com.chikanov.gstore.services;

import com.chikanov.gstore.entity.ChatEntity;
import com.chikanov.gstore.entity.dto.ChatDTO;
import com.chikanov.gstore.entity.tgentities.Chat;
import com.chikanov.gstore.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public ChatEntity getOrCreate(Chat tgChat)
    {
        return chatRepository.findById(tgChat.getId()).orElseGet(()->{
            ChatEntity newChat = new ChatEntity();
            newChat.setId(tgChat.getId());
            newChat.setName(tgChat.getTitle());
            return newChat;
        });
    }

    public ChatDTO convertToDTO(ChatEntity chat){
        return new ChatDTO(chat.getName(), chat.getId());
    }
}
