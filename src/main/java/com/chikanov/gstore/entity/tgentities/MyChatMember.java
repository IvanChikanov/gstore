package com.chikanov.gstore.entity.tgentities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MyChatMember {
    private Chat chat;
    private TgUser from;
    @JsonProperty("old_chat_member")
    private ChatMember oldMember;
    @JsonProperty("new_chat_member")
    private ChatMember newMember;
}
