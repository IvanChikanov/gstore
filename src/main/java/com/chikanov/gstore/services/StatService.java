package com.chikanov.gstore.services;

import com.chikanov.gstore.entity.Result;
import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.entity.tgentities.TgUser;
import com.chikanov.gstore.repositories.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public Map<String, Integer> findAllResults(TgUser user){

        User dBuser = userService.getById(user.getId());
        var all = resultRepository.findByUser(dBuser);
        var res = all.orElse(new ArrayList<>());
        Map<String, Integer> calculatedStats = new HashMap<>();
        res.forEach(r -> {
            String name = r.getGame().getGameType().getName();
            calculatedStats.put(name, calculatedStats.containsKey(name) ?
                    calculatedStats.get(name) +  r.getPoints() : r.getPoints());
        });
        return calculatedStats;
    }
}
