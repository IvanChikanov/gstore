package com.chikanov.gstore.records;

import com.chikanov.gstore.entity.User;

public record WsPlayer(WsUser wsUser, User dbUser) {
}
