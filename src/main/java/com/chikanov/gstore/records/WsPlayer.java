package com.chikanov.gstore.records;

import com.chikanov.gstore.entity.Result;
import com.chikanov.gstore.entity.User;
import org.springframework.web.socket.WebSocketSession;

public record WsPlayer(WebSocketSession session, User dbUser , Result result) {
}
