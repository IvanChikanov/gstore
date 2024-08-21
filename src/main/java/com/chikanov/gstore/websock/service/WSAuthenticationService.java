package com.chikanov.gstore.websock.service;

import com.chikanov.gstore.entity.ChatEntity;
import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.entity.tgentities.TgUser;
import com.chikanov.gstore.enums.Role;
import com.chikanov.gstore.enums.TypesOfMessage;
import com.chikanov.gstore.exceptions.WsException;
import com.chikanov.gstore.exceptions.enums.WsExceptionType;
import com.chikanov.gstore.filters.Authenticator;
import com.chikanov.gstore.records.AuthData;
import com.chikanov.gstore.records.AuthenticationMessage;

import com.chikanov.gstore.services.ChatRoleService;
import com.chikanov.gstore.services.GameService;
import com.chikanov.gstore.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WSAuthenticationService {

    @Autowired
    private Authenticator authenticator;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ChatRoleService chatRoleService;

    @Autowired
    private GameService gameService;

    @Autowired
    private WsMessageConverter wsMessageConverter;

    private final ConcurrentHashMap<String, WebSocketSession> unauthorizedSessions = new ConcurrentHashMap<>();

    @Transactional
    public User authenticate(AuthenticationMessage authMessage) throws WsException {
        try {
            AuthData auth = authenticator.validation(authMessage.token());
            if (auth.statusCode().equals(HttpStatus.OK)) {
                User user = userService.getOrCreate(objectMapper.readValue(auth.result(), TgUser.class));
                ChatEntity chat = gameService.getChatFromGameId(authMessage.game());
                var chr = user.getChatRoles().stream().filter(cr -> cr.getChat().getId().equals(chat.getId())).findFirst();
                if (chr.isEmpty()) {
                    user.getChatRoles().add(chatRoleService.createChatRole(user, chat, Role.USER));
                    userService.saveUser(user);
                }
                var s = unauthorizedSessions.remove(authMessage.from());
                return user;
            }
            throw new WsException("Ошибка авторизации, вы случаем не злоумышленник?!", WsExceptionType.AUTH_ERROR);
        }catch (JsonProcessingException jsonProcessingException){
            throw new WsException("Некорректный пользователь!", WsExceptionType.INVALID_JSON);
        }
    }

    public void addToWaitList(WebSocketSession session) throws WsException {
        try {
            unauthorizedSessions.put(session.getId(), session);
            session.sendMessage(new TextMessage(wsMessageConverter.createFullMessage(TypesOfMessage.AUTH, 0, session.getId())));
        }catch (IOException ioException){
            throw new WsException("Сессия не найдена!", WsExceptionType.SESSION_ERROR);
        }
    }
}
