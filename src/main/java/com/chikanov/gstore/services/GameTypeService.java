package com.chikanov.gstore.services;

import com.chikanov.gstore.entity.GameType;
import com.chikanov.gstore.entity.dto.GameTypeDTO;
import com.chikanov.gstore.entity.dto.GameTypeFilteredDTO;
import com.chikanov.gstore.enums.TypesOfGame;
import com.chikanov.gstore.repositories.GameTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameTypeService {

    @Autowired
    private GameTypeRepository gameTypeRepository;

    public void updateGameList(List<GameType> games){
        gameTypeRepository.saveAll(games);
    }

    public GameTypeFilteredDTO getActiveGames()
    {
        var entities = gameTypeRepository.findByIsActive(true);
        GameTypeFilteredDTO filteredDTO = new GameTypeFilteredDTO();
        var grouped = entities.stream().collect(Collectors.groupingBy(GameType::getType));
        filteredDTO.setCoop(grouped.get(TypesOfGame.COOPERATION).stream().map(this::convertToDTO).toList());
        filteredDTO.setSingle(grouped.get(TypesOfGame.SINGLE).stream().map(this::convertToDTO).toList());
        return filteredDTO;
    }

    private GameTypeDTO convertToDTO(GameType gt)
    {
        GameTypeDTO gameTypeDTO = new GameTypeDTO();
        gameTypeDTO.setName(gt.getName());
        gameTypeDTO.setSrc(gt.getImgSrc());
        gameTypeDTO.setModule(gt.getModule());
        return gameTypeDTO;
    }
}
