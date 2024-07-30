package com.chikanov.gstore.entity.tgentities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TgMessage {

    @JsonProperty("chat_id")
    private Long chatId;

    private String text;

    @JsonProperty("reply_markup")
    private List<List<InlineKeyboard>> replyMarkup = new ArrayList<>();

}
