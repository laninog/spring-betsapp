package com.betsapp.bets.web;

import com.betsapp.BetsAppProperties;
import com.betsapp.bets.services.UserBetDTO;
import com.betsapp.bets.services.UserBetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserBetResource {

    @Autowired
    private UserBetService userBetService;

    @PostMapping("/user-bets")
    public UserBetDTO create(@RequestBody UserBetDTO userBet) {
        return userBetService.create(userBet);
    }

    @GetMapping("/user-bets")
    public List<UserBetDTO> findAll() {
        return userBetService.findAll();
    }

}
