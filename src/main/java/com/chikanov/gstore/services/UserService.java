package com.chikanov.gstore.services;

import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.entity.tgentities.TgUser;
import com.chikanov.gstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<User> getUserFromTg(TgUser tgUser)
    {
        return userRepository.findById(tgUser.getId());
    }
    public User createUser(TgUser tgUser)
    {
        User user = new User();
        user.setId(tgUser.getId());
        user.setPremium(false);
        userRepository.save(user);
        return user;
    }
    public User getOrCreate(TgUser tgUser)
    {
        var us = getUserFromTg(tgUser);
        return us.orElseGet(() -> createUser(tgUser));
    }

    public User getById(Long id){
        return userRepository.findById(id).orElseThrow();
    }
}
