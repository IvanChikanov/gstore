package com.chikanov.gstore.entity.tgentities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InlineQuery {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("chat_type")
    private String chatType;
}
