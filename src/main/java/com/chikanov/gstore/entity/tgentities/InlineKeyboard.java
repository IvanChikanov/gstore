package com.chikanov.gstore.entity.tgentities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class InlineKeyboard {

    @JsonProperty("inline_keyboard")
    private List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
}
