package com.chikanov.gstore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Game {
    @Id
    private UUID id;
}
