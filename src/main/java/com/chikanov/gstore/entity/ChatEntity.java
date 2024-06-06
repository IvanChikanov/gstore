package com.chikanov.gstore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
@Entity
@Data
public class ChatEntity{

    @Id
    private Long chat_id;

    private String name;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<ChatRoles> chatRoles = new HashSet<>();
}
