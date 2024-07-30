package com.chikanov.gstore.services.tgservice;

import com.chikanov.gstore.entity.tgentities.InlineKeyboard;
import com.chikanov.gstore.entity.tgentities.InlineKeyboardButton;
import com.chikanov.gstore.entity.tgentities.TgMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    public TgMessage oneButtonMessage(String text, Long chatId, InlineKeyboard oneButton)
    {
        TgMessage message = new TgMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setReplyMarkup(oneButton);
        return  message;
    }
    public InlineKeyboard oneButtonKeyboard(String text, String url)
    {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setUrl(url);
        List<InlineKeyboardButton> inline = new ArrayList<>();
        inline.add(inlineKeyboardButton);
        InlineKeyboard inlineKeyboard = new InlineKeyboard();
        inlineKeyboard.getButtons().add(inline);
        return inlineKeyboard;
    }
}
