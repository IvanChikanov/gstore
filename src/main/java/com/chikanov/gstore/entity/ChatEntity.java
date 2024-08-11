package com.chikanov.gstore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
@Entity
@Data
public class ChatEntity{

    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "chat")
    private Set<ChatRole> chatRoles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "chatId")
    private Set<Game> games = new HashSet<>();
}
