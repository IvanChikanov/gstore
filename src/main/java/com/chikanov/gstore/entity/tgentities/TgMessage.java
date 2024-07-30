package com.chikanov.gstore.entity.tgentities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TgMessage {

    @JsonProperty("chat_id")
    private Long chatId;

    private String text;

    @JsonProperty("reply_markup")
    private InlineKeyboard replyMarkup;

}
