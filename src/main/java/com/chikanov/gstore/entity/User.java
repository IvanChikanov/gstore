package com.chikanov.gstore.entity;

import com.chikanov.gstore.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Entity
@Data
@Table(name = "tg_user")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hashedId;

    private String customName;

    private boolean isPremium;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<ChatRoles> chatRoles = new HashSet<>();
}
