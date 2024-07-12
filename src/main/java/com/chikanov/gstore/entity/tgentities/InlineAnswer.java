package com.chikanov.gstore.entity.tgentities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InlineAnswer {

    @JsonProperty("inline_query_id")
    private Long inlineQueryId;

    @JsonProperty("button")
    private InlineButton button = new InlineButton();
}
