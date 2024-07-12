package com.chikanov.gstore.entity.tgentities;

import lombok.Data;

@Data
public class InlineAnswer {

    private Long inlineQueryId;

    private int[] results = new int[0];

    private InlineButton button = new InlineButton();
}
