package com.chikanov.gstore.entity;

import jakarta.persistence.*;
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
    @JoinColumn(name = "chat_id")
    private ChatEntity chatId;

    @OneToMany(mappedBy = "id")
    private Set<Result> results = new HashSet<>();

    private boolean finished;
}
