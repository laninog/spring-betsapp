package com.betsapp.bets.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.betsapp.bets.domain.ClientBet;
import com.betsapp.bets.mappers.ClientBetMapper;
import com.betsapp.bets.repositories.ClientBetRepository;
import com.betsapp.exceptions.NotFoundException;

@Service
public class ClientBetService {

    //@Autowired
    private ClientBetMapper mapper;

    //@Autowired
    private ClientBetRepository clientBetRepository;

    //@Autowired
    //private UserRepository clientRepository;

    //@Autowired
    //private MatchRepository matchRepository;

    @Autowired
    public ClientBetService(ClientBetMapper mapper, ClientBetRepository clientBetRepository) {
        this.mapper = mapper;
        this.clientBetRepository = clientBetRepository;
    }

    @Transactional(readOnly=true)
    public List<ClientBetDTO> findAll() {
        return mapper.toDto(clientBetRepository.findAll());
    }

    @Transactional(readOnly=true)
    public List<ClientBetDTO> findAllByClient(Long clientId) {
        //Optional<User> client = clientRepository.findById(clientId);
        //client.orElseThrow(() -> new NotFoundException("User not found"));

        /*ClientBet criteria = new ClientBet();
        criteria.setUser(client.get());*/

        Example<ClientBet> example = Example.of(null);

        return mapper.toDto(clientBetRepository.findAll(example));
    }

    @Transactional
    public ClientBetDTO findByIdAndClient(Long clientId, Long id) {
    	Map<String, String> pathVars = new HashMap<>();
		pathVars.put("id", String.valueOf(clientId));
		Client client = new RestTemplate().getForObject("http://localhost:8000/users/{id}", Client.class, pathVars);
    	
        Optional<ClientBet> ub = clientBetRepository.findById(id);
        ub.orElseThrow(() -> new NotFoundException("Bet not found!"));

        return mapper.toDto(clientBetRepository.findByIdAndClient(id, clientId).get());
    }

    @Transactional
    public ClientBetDTO create(ClientBetDTO clientBet) {

        /*Optional<User> client = clientRepository.findById(clientBet.getUser());
        client.orElseThrow(() -> new NotFoundException("User not found"));

        Optional<Match> match = matchRepository.findByIdOptional(clientBet.getMatch());
        match.orElseThrow(() -> new NotFoundException("Match not found"));*/

        ClientBet newUserBet = mapper.toEntity(clientBet);
        newUserBet.setCreated(LocalDateTime.now());

        return mapper.toDto(clientBetRepository.save(newUserBet));
    }

    @Transactional
    public ClientBetDTO findById(Long id) {
        Optional<ClientBet> clientBet = clientBetRepository.findById(id);
        clientBet.orElseThrow(() -> new NotFoundException("UserBet not found"));
        return mapper.toDto(clientBet.get());
    }

}
