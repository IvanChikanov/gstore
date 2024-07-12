package com.chikanov.gstore.entity.tgentities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InlineButton {
    @JsonProperty("text")
    private String text;
    @JsonProperty("web_app")
    private WebAppInfo webApp = new WebAppInfo();
}
