package com.chikanov.gstore.websock.messages;

import lombok.Data;

import java.util.UUID;
@Data
public class AbstractMessage {
    private UUID from;
    private UUID game;
}
