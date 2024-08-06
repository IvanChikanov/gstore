package com.chikanov.gstore.records;

import java.util.UUID;

public record AuthorizeMessage(UUID token, String type) {
}
