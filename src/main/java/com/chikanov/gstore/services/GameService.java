package com.chikanov.gstore.services;

import com.chikanov.gstore.entity.ChatEntity;
import com.chikanov.gstore.entity.Game;
import com.chikanov.gstore.repositories.ChatRepository;
import com.chikanov.gstore.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class GameService {

    @Autowired
    private GameTypeService gameTypeService;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Transactional
    public String createGame(Long chatId, Long gameId)
    {
        Game game = new Game();
        game.setId(UUID.randomUUID());
        game.setGameType(gameTypeService.getGame(gameId));
        ChatEntity chat = chatRepository.findById(chatId).orElseThrow();
        game.setChatId(chat);
        chat.getGames().add(game);
        chatRepository.save(chat);
        return game.getId().toString();
    }

    public Game getGame(UUID id){
        return gameRepository.findByIdAndFinished(id, false).orElseThrow();
    }

    public String getGameModule(UUID uuid){
        Game game = gameRepository.findById(uuid).orElseThrow();
        return game.getGameType().getModule();
    }

    public void saveGame(Game game){
        gameRepository.save(game);
    }

    public ChatEntity getChatFromGameId(UUID id){
        return gameRepository.findById(id).orElseThrow().getChatId();
    }

}
