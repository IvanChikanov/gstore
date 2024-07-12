package com.chikanov.gstore.entity.tgentities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TgQueries {

    @JsonProperty("update_id")
    private Long update;

    @JsonProperty("inline_query")
    private InlineQuery inlineQuery;
}
