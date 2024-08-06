package com.chikanov.gstore.records;

import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

public record WsUser(UUID externalId, WebSocketSession session) {
}
