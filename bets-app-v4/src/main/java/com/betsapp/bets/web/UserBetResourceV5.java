package com.betsapp.bets.web;

import com.betsapp.bets.services.UserBetDTO;
import com.betsapp.bets.services.UserBetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class UserBetResourceV5 {

    private static final Logger logger = LoggerFactory.getLogger(UserBetResourceV5.class);

    @Autowired
    private UserBetService userBetService;

    @PostMapping(value="/users/{user}/bets", produces = "application/vnd.company.app-v5+json")
    public ResponseEntity<UserBetDTO> create(@PathVariable("user") Long user,
                                             @Valid @RequestBody UserBetDTO userBet) {
		UserBetDTO dto = userBetService.create(userBet);

		// Http 201 debe agregar una cabecera con la localizacion del nuevo recurso /api/user-bets/{id} 
		// Cambiar path a fragment si se utiliza una SPA o PWD
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId())
				.toUri();

        logger.info("Form API V5");

		return ResponseEntity.created(uri).build();
    }

    @GetMapping(value="/users/{user}/bets", produces = "application/vnd.company.app-v5+json")
    public ResponseEntity<List<Resource<UserBetDTO>>> findAllByUser(
            @PathVariable("user") Long user) {
    	
    	// HATEOAS
    	// UserDTO podría extender de ResourceSupport (clase padre de Resource) 
    	List<Resource<UserBetDTO>> results = 
    			userBetService.findAllByUser(user)
    				.stream().map(ub -> 
    					new Resource<>(ub, linkTo(methodOn(this.getClass()).findById(user, ub.getId())).withSelfRel())
    				).collect(Collectors.toList());
    	
    	return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /*@GetMapping("/user-bets/{id}")
    public Resource<UserBetDTO> findById(@PathVariable("id") Long id) {
        UserBetDTO userBet = userBetService.findById(id);

        Resource<UserBetDTO> resource = new Resource<>(userBet);

        // HATEOAS 
        ControllerLinkBuilder link =
                ControllerLinkBuilder.linkTo(
                        ControllerLinkBuilder.methodOn(this.getClass()).findAll());

        resource.add(link.withRel("findAll"));

        return resource;
    }*/
    
    @GetMapping(value="/user/{user}/bets/{id}", produces = "application/vnd.company.app-v5+json")
    public ResponseEntity<Resource<UserBetDTO>> findById(@PathVariable("user") Long user,
                                                         @PathVariable("id") Long id) {
        UserBetDTO userBet = userBetService.findByIdAndUser(user, id);

        Resource<UserBetDTO> resource = new Resource<>(userBet);

        // HATEOAS 
        resource.add(linkTo(methodOn(this.getClass()).findAllByUser(user)).withRel("findAll"));

        // Response Entity recubre resource 
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

}
