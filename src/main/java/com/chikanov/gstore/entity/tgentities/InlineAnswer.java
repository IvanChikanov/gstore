package com.chikanov.gstore.entity.tgentities;

import lombok.Data;

@Data
public class InlineAnswer {

    private Long inlineQueryId;

    private InlineButton button = new InlineButton();
}
