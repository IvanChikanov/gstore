package com.chikanov.gstore.records;

import java.util.UUID;

public record ActionMessage(UUID from, UUID game, String jsonAction) {
}
