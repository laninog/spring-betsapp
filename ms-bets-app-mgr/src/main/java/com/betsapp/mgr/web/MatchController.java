package com.betsapp.mgr.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.betsapp.Configuration;
import com.betsapp.exceptions.NotFoundException;
import com.betsapp.mgr.domain.Match;
import com.betsapp.mgr.repositories.MatchRepository;

@RestController
public class MatchController {

	private static final Logger logger = LoggerFactory.getLogger(MatchController.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private Configuration config;
	
	@Autowired
	private MatchRepository repository;
	
    @PostMapping("/matches")
    public ResponseEntity<Match> create(@Valid @RequestBody Match match) {
		Match newMatch = repository.save(match);
		
		// It should be a business rule, it should be part of a service
		if (match.getOpen() == null) {
			match.setOpen(getOpenDate());
			match.setClose(getCloseDate());
		}

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newMatch.getId())
				.toUri();
		
		logger.info("Port {}", env.getProperty("local.server.port"));

		return ResponseEntity.created(uri).build();
    }
    
    private LocalDateTime getOpenDate() {
    	return LocalDateTime.now();
    }
    
    private LocalDateTime getCloseDate() {
    	return LocalDateTime.now().plus(config.getDefaultTimeWindow(), ChronoUnit.DAYS);
    }
    
    @GetMapping("/matches/{id}")
    public ResponseEntity<Resource<Match>> findById(@PathVariable("id") Long id) {
    	Optional<Match> opMatch = repository.findById(id);
    	opMatch.orElseThrow(() -> new NotFoundException("Match not found"));

    	Resource<Match> resource = new Resource<>(opMatch.get());
    	
        resource.add(linkTo(methodOn(this.getClass()).findAll()).withRel("findAll"));

        logger.info("Port {}", env.getProperty("local.server.port"));
        
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @GetMapping("/matches")
    public ResponseEntity<List<Resource<Match>>> findAll() {
    	
    	List<Resource<Match>> results =
    			repository.findAll().stream()
    				.map(m -> 
    					new Resource<>(m, linkTo(methodOn(this.getClass()).findById(m.getId())).withSelfRel())
    				).collect(Collectors.toList());
    	
    	logger.info("Port {}", env.getProperty("local.server.port"));
    	
    	return new ResponseEntity<>(results, HttpStatus.OK);
    }
    
    @PutMapping("/matches/{id}")
    public ResponseEntity<Match> update(@PathVariable("id") Long id, @RequestBody Match updatedMatch) {
    	Optional<Match> opMatch = repository.findById(id);
    	opMatch.orElseThrow(() -> new NotFoundException("Match not found"));
    	
    	Match oldMatch = opMatch.get();
    	oldMatch.setResult(updatedMatch.getResult());
    	
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(oldMatch.getId())
				.toUri();
		
		logger.info("Port {}", env.getProperty("local.server.port"));
    	    	
    	return ResponseEntity.noContent().location(uri).build();
    }
	
}
