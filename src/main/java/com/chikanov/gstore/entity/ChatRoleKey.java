package com.chikanov.gstore.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class ChatRoleKey implements Serializable {
    private long user;
    private long chat;
}
