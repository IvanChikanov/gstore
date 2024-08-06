package com.chikanov.gstore.websock.messages;

import com.chikanov.gstore.games.IPayload;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@JsonDeserialize(using = WsMessageDeserializer.class)
public class WsMessage<T extends IPayload> {

    private String type;
    private String from;
    private UUID game;
    private T payload;
}
