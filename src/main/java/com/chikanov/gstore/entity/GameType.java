package com.chikanov.gstore.entity;

import com.chikanov.gstore.enums.TypesOfGame;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class GameType {

    @Id
    private String name;

    private String module;

    @JsonProperty("src")
    private String imgSrc;

    @Enumerated(EnumType.STRING)
    private TypesOfGame type;
    @JsonProperty("is_active")
    private boolean isActive;
}
