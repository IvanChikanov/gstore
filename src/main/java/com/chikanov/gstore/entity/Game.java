package com.chikanov.gstore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Game {
    @Id
    private UUID id;

    @ManyToOne
    private ChatEntity chatId;

    @OneToMany(mappedBy = "id")
    private Set<Result> results = new HashSet<>();

    private boolean finished;
}
