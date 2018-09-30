package com.betsapp.bets.services;

import com.betsapp.bets.mappers.UserBetMapper;
import com.betsapp.bets.repositories.UserBetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBetService {

    @Autowired
    private UserBetMapper mapper;

    @Autowired
    private UserBetRepository userBetRespository;

    public List<UserBetDTO> findAll() {
        return mapper.toDto(userBetRespository.findAll());
    }

}
