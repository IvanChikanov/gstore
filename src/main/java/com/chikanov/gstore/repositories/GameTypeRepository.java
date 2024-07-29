package com.chikanov.gstore.repositories;

import com.chikanov.gstore.entity.GameType;
import com.chikanov.gstore.entity.dto.GameTypeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameTypeRepository extends JpaRepository<GameType, String> {

    List<GameType> findByIsActive(boolean isActive);
}
