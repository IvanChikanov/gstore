package com.chikanov.gstore.websock.messages.impl;

import com.chikanov.gstore.websock.messages.AbstractMessage;
import lombok.Data;


@Data
public class AuthMessage extends AbstractMessage {
    private String token;
}
