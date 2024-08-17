package com.chikanov.gstore.records;

import com.chikanov.gstore.enums.TypesOfMessage;
import org.springframework.web.socket.WebSocketSession;

public record Message(TypesOfMessage tos, int num, String payload, WebSocketSession session) {
}
