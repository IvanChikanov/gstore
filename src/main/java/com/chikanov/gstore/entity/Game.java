package com.chikanov.gstore.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Game {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "game_type_id")
    private GameType gameType;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatEntity chatId;

    @OneToMany(mappedBy = "game")
    private Set<Result> results = new HashSet<>();

    private boolean finished = false;
}
