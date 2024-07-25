package com.chikanov.gstore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int points;
    private boolean winner;

    @ManyToOne
    private Game game;

}
