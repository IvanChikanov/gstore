package com.chikanov.gstore.services;

import com.chikanov.gstore.entity.Result;
import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.entity.tgentities.TgUser;
import com.chikanov.gstore.repositories.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public ResponseEntity<List<Stat>> findAllResults(TgUser user){
        List<Stat> result = new ArrayList<>();
        User dBuser = userService.getById(user.getId());
        var all = resultRepository.findByUser(dBuser);
        var res = all.orElse(new ArrayList<>());
        var grouped = res.stream().collect(Collectors.groupingBy(r -> r.getGame().getGameType().getName()));
        grouped.keySet().forEach(k->
            result.add(new Stat(k, grouped.get(k).stream().filter(Result::isWinner).count(), grouped.size())));
        return ResponseEntity.ok(result);
    }

    private record Stat(String gameName, long wins, int games){};
}
