package com.chikanov.gstore.entity;

import com.chikanov.gstore.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "tg_user")
public class User extends AbstractEntity{

    private String hashedId;

    private String customName;

    private boolean isPremium;

    @Transient
    private Role role;
}
