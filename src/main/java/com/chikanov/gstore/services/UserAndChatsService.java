package com.chikanov.gstore.services;

import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.repositories.ChatRoleRepository;
import com.chikanov.gstore.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserAndChatsService {

    private final ChatRoleRepository chatRoleRepository;
    private final UserRepository userRepository;

    public Optional<User> loadUser(String id)
    {
        return userRepository.findByHashedId(id);
    }
    public User createUser(String id)
    {
        User user = new User();
        user.setPremium(false);
        user.setHashedId(id);
        return user;
    }
}
