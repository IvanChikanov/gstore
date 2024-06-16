package com.chikanov.gstore.services;

import com.chikanov.gstore.entity.ChatEntity;
import com.chikanov.gstore.entity.Game;
import com.chikanov.gstore.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public String createGame(ChatEntity chatId)
    {
        Game game = new Game();
        game.setId(UUID.randomUUID());
        game.setChatId(chatId);
        gameRepository.save(game);
        return game.getId().toString();
    }
}
