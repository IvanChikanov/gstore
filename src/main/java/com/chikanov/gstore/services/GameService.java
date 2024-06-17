package com.chikanov.gstore.services;

import com.chikanov.gstore.entity.ChatEntity;
import com.chikanov.gstore.entity.Game;
import com.chikanov.gstore.repositories.ChatRepository;
import com.chikanov.gstore.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ChatRepository chatRepository;

    public String createGame(Long chatId)
    {
        Game game = new Game();
        game.setId(UUID.randomUUID());
        ChatEntity chat = chatRepository.findById(chatId).orElseThrow();
        game.setChatId(chat);
        chat.getGames().add(game);
        chatRepository.save(chat);
        return game.getId().toString();
    }
}
