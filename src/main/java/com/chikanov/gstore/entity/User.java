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
    private String id;

    private String customName;

    private boolean isPremium;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<ChatRoles> chatRoles = new HashSet<>();
}
