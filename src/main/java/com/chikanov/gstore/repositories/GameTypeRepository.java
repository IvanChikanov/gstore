package com.chikanov.gstore.repositories;

import com.chikanov.gstore.entity.GameType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameTypeRepository extends JpaRepository<GameType, String> {

    List<GameType> findByIsActive(boolean isActive);
}
