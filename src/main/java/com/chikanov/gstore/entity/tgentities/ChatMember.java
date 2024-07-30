package com.chikanov.gstore.entity.tgentities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatMember {
    @JsonProperty("user")
    private TgUser user;

    @JsonProperty("status")
    private String status;
}
