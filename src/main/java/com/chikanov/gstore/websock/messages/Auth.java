package com.chikanov.gstore.websock.messages;

import com.chikanov.gstore.games.IPayload;
import lombok.Data;

@Data
public class Auth implements IPayload {
    private String token;

    @Override
    public String load() {

        return token;
    }
}
