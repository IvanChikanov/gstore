package com.chikanov.gstore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Result {
    @Id
    private ChatRoleKey id;
    private int points;
    private boolean winner;

    @ManyToOne
    private Game game;

}
