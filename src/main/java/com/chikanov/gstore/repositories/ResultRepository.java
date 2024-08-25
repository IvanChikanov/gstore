package com.chikanov.gstore.repositories;

import com.chikanov.gstore.entity.Result;
import com.chikanov.gstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    Optional<List<Result>> findByUser(User user);
}
