package com.chikanov.gstore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
@Entity
@Data
@Table(name = "tg_user")
public class User{
    @Id
    private Long id;

    private String customName;

    private boolean isPremium;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<ChatRole> chatRoles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Result> results = new HashSet<>();
}
