package com.betsapp.bets.services;

import com.betsapp.bets.domain.ClientBet;
import com.betsapp.bets.exceptions.NotFoundException;
import com.betsapp.bets.mappers.ClientBetMapper;
import com.betsapp.bets.repositories.ClientBetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClientBetService {

    //@Autowired
    private ClientBetMapper mapper;

    //@Autowired
    private ClientBetRepository userBetRepository;

    //@Autowired
    //private UserRepository userRepository;

    //@Autowired
    //private MatchRepository matchRepository;

    @Autowired
    public ClientBetService(ClientBetMapper mapper, ClientBetRepository userBetRepository) {
        this.mapper = mapper;
        this.userBetRepository = userBetRepository;
    }

    @Transactional(readOnly=true)
    public List<ClientBetDTO> findAll() {
        return mapper.toDto(userBetRepository.findAll());
    }

    @Transactional(readOnly=true)
    public List<ClientBetDTO> findAllByUser(Long userId) {
        //Optional<User> user = userRepository.findById(userId);
        //user.orElseThrow(() -> new NotFoundException("User not found"));

        /*ClientBet criteria = new ClientBet();
        criteria.setUser(user.get());*/

        Example<ClientBet> example = Example.of(null);

        return mapper.toDto(userBetRepository.findAll(example));
    }

    @Transactional
    public ClientBetDTO findByIdAndUser(Long userId, Long id) {
        /*Optional<User> user = userRepository.findById(userId);
        user.orElseThrow(() -> new NotFoundException("User not found"));*/

        Optional<ClientBet> ub = userBetRepository.findById(id);
        ub.orElseThrow(() -> new NotFoundException("Bet not found!"));

        //return mapper.toDto(userBetRepository.findByIdAndUserId(id, userId).get());
        return null;
    }

    @Transactional
    public ClientBetDTO create(ClientBetDTO userBet) {

        /*Optional<User> user = userRepository.findById(userBet.getUser());
        user.orElseThrow(() -> new NotFoundException("User not found"));

        Optional<Match> match = matchRepository.findByIdOptional(userBet.getMatch());
        match.orElseThrow(() -> new NotFoundException("Match not found"));*/

        ClientBet newUserBet = mapper.toEntity(userBet);
        newUserBet.setCreated(LocalDateTime.now());

        return mapper.toDto(userBetRepository.save(newUserBet));
    }

    @Transactional
    public ClientBetDTO findById(Long id) {
        Optional<ClientBet> userBet = userBetRepository.findById(id);
        userBet.orElseThrow(() -> new NotFoundException("UserBet not found"));
        return mapper.toDto(userBet.get());
    }

}
