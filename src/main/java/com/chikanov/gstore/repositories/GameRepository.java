package com.chikanov.gstore.repositories;

import com.chikanov.gstore.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {

    Optional<Game> findByIdAndFinished(UUID id, boolean finished);

}
