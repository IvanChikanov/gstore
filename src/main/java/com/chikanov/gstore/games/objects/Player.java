package com.chikanov.gstore.games.objects;

import com.chikanov.gstore.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

@Getter
@Setter
public class Player<T>{
    private WebSocketSession session;
    private User user;
    private T realTimeData;

    public void replaceSession(WebSocketSession newSession){
        session = newSession;
    }

}
