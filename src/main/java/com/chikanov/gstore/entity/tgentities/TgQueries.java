package com.chikanov.gstore.entity.tgentities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TgQueries {

    @JsonIgnore
    private String type;

    @JsonProperty("update_id")
    private Long update;

    @JsonProperty("inline_query")
    private InlineQuery inlineQuery;

    @JsonProperty("my_chat_member")
    private MyChatMember myChatMember;

    public String getType()
    {
        if(inlineQuery != null)
            type = "inline";
        if(myChatMember != null)
            type = "chatMember";
        return type;
    }
}
