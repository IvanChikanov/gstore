package com.chikanov.gstore.entity;

import com.chikanov.gstore.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "chat_role")
public class ChatRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id")
    private ChatEntity chat;

    @Enumerated(EnumType.STRING)
    private Role role;
}
