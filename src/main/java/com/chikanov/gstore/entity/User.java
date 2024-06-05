package com.chikanov.gstore.entity;

import com.chikanov.gstore.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tg_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hashedId;

    private String customName;

    private boolean isPremium;

    @Transient
    private Role role;
}
