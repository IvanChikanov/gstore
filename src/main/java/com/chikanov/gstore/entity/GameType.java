package com.chikanov.gstore.entity;

import com.chikanov.gstore.enums.TypesOfGame;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class GameType {
    @Id
    private Long id;

    private String name;

    private String module;

    @JsonProperty("src")
    private String imgSrc;

    @Enumerated(EnumType.STRING)
    private TypesOfGame type;

    @JsonProperty("is_active")
    private boolean isActive;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gameType")
    private Set<Game> games = new HashSet<>();
}
