package com.betsapp.bets.web;

import com.betsapp.mgr.domain.Match;
import com.betsapp.mgr.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MatchResource {

    @Autowired
    private MatchRepository matchRepository;

    @GetMapping("/matches")
    public List<Match> findAll() {
        return matchRepository.findAll();
    }

}
