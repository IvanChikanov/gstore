package com.chikanov.gstore.entity.tgentities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InlineKeyboardButton {

    private String text;
    private String url;

    @JsonProperty("callback_data")
    private String callback;
    @JsonProperty("web_app")
    private WebAppInfo webApp;


}
