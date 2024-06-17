package com.chikanov.gstore.enums;

public enum TelegramUpdates {
    /*MESSAGE("message"),*/
    INLINE_QUERY("inline_query"),
    CHOSEN_INLINE_RESULT("chosen_inline_result"),
    CALLBACK_QUERY("callback_query"),
    MY_CHAT_MEMBER("my_chat_member");
    private String str;
    TelegramUpdates(String text)
    {
        str = text;
    }
    public String getStr()
    {
        return str;
    }
}
