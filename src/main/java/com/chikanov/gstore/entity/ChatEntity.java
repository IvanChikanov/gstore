package com.chikanov.gstore.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Entity
@Data
public class ChatEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long chat_id;

    private String name;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<ChatRoles> chatRoles = new HashSet<>();
}
