package com.chikanov.gstore.records;

import java.util.UUID;

public record AuthenticationMessage(UUID from, UUID game, String token) {
}
