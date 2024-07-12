package com.chikanov.gstore.entity.tgentities;

import lombok.Data;

@Data
public class InlineButton {
    private String text;
    private WebAppInfo webApp = new WebAppInfo();
}
