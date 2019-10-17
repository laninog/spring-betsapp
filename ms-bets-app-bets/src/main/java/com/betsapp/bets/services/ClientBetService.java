package com.betsapp.bets.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.betsapp.bets.clients.MatchesClientProxy;
import com.betsapp.bets.clients.User;
import com.betsapp.bets.clients.UsersClientProxy;
import com.betsapp.bets.domain.ClientBet;
import com.betsapp.bets.mappers.ClientBetMapper;
import com.betsapp.bets.repositories.ClientBetRepository;
import com.betsapp.exceptions.NotFoundException;

@Service
public class ClientBetService {

    private ClientBetMapper mapper;

    private ClientBetRepository clientBetRepository;

    private UsersClientProxy usersClientProxy;
    private MatchesClientProxy matchesClientProxy;

    @Autowired
    public ClientBetService(
    		ClientBetMapper mapper, 
    		ClientBetRepository clientBetRepository,
    		UsersClientProxy usersClientProxy,
    		MatchesClientProxy matchesClientProxy) {
        this.mapper = mapper;
        this.clientBetRepository = clientBetRepository;
        this.usersClientProxy = usersClientProxy;
        this.matchesClientProxy = matchesClientProxy;
    }

    @Transactional(readOnly=true)
    public List<ClientBetDTO> findAll() {
        return mapper.toDto(clientBetRepository.findAll());
    }

    @Transactional(readOnly=true)
    public List<ClientBetDTO> findAllByClient(Long clientId) {

    	// Client exists validation
    	User user = usersClientProxy.findById(clientId, getAuthorizationHeader());
    	
        ClientBet criteria = new ClientBet();
        criteria.setClient(user.getId());

        Example<ClientBet> example = Example.of(criteria);

        return mapper.toDto(clientBetRepository.findAll(example));
    }

    @Transactional
    public ClientBetDTO findByIdAndClient(Long clientId, Long id) {
    	/*Map<String, String> pathVars = new HashMap<>();
		pathVars.put("id", String.valueOf(clientId));
		Client client = new RestTemplate().getForObject("http://localhost:8000/users/{id}", Client.class, pathVars);*/
    	
    	// Client exists validation
    	usersClientProxy.findById(clientId, getAuthorizationHeader());
    	
        Optional<ClientBet> ub = clientBetRepository.findById(id);
        ub.orElseThrow(() -> new NotFoundException("Bet not found!"));

        return mapper.toDto(clientBetRepository.findByIdAndClient(id, clientId).get());
    }

    @Transactional
    public ClientBetDTO create(ClientBetDTO clientBet) {
    	
    	// Client exists validation
    	usersClientProxy.findById(clientBet.getClient(), getAuthorizationHeader());

        // Match exists validation
        matchesClientProxy.findById(clientBet.getClient(), getAuthorizationHeader());

        ClientBet newUserBet = mapper.toEntity(clientBet);
        newUserBet.setCreated(LocalDateTime.now());

        return mapper.toDto(clientBetRepository.save(newUserBet));
    }

    @Transactional
    public ClientBetDTO findById(Long id) {
        Optional<ClientBet> clientBet = clientBetRepository.findById(id);
        clientBet.orElseThrow(() -> new NotFoundException("Bet not found!"));
        return mapper.toDto(clientBet.get());
    }
    
    private String getAuthorizationHeader() {
    	return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
    }

}
