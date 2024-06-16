package com.chikanov.gstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
public class ChatRoleKey implements Serializable {
    private String user;
    private Long chat;
}
