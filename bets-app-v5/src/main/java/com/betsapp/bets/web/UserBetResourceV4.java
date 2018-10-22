package com.betsapp.bets.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.betsapp.bets.services.UserBetDTO;
import com.betsapp.bets.services.UserBetService;

@RestController
@RequestMapping("/api")
public class UserBetResourceV4 {

    private static final Logger logger = LoggerFactory.getLogger(UserBetResourceV4.class);

    @Autowired
    private UserBetService userBetService;

    @PostMapping(value="/users/{user}/bets", headers = "X-API-VERSION=4")
    public ResponseEntity<UserBetDTO> create(@PathVariable("user") Long user,
                                             @Valid @RequestBody UserBetDTO userBet) {
		UserBetDTO dto = userBetService.create(userBet);

		// Http 201 debe agregar una cabecera con la localizacion del nuevo recurso /api/user-bets/{id} 
		// Cambiar path a fragment si se utiliza una SPA o PWD
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId())
				.toUri();

        logger.info("Form API V4");

        return ResponseEntity.created(uri).header("X-API-VERSION", "4").build();
    }

    @GetMapping(value="/users/{user}/bets", headers = "X-API-VERSION=4")
    public ResponseEntity<List<Resource<UserBetDTO>>> findAllByUser(
            @PathVariable("user") Long user) {
    	
    	// HATEOAS
    	// UserDTO podría extender de ResourceSupport (clase padre de Resource) 
    	List<Resource<UserBetDTO>> results = 
    			userBetService.findAllByUser(user)
    				.stream().map(ub -> 
    					new Resource<>(ub, linkTo(methodOn(this.getClass()).findById(user, ub.getId())).withSelfRel())
    				).collect(Collectors.toList());
    	
    	return ResponseEntity.ok().header("X-API-VERSION", "4").body(results);
    }
    
    @GetMapping(value="/users/{user}/bets/{id}", headers = "X-API-VERSION=4")
    public ResponseEntity<Resource<UserBetDTO>> findById(@PathVariable("user") Long user,
                                                         @PathVariable("id") Long id) {
        UserBetDTO userBet = userBetService.findByIdAndUser(user, id);

        Resource<UserBetDTO> resource = new Resource<>(userBet);

        // HATEOAS 
        resource.add(linkTo(methodOn(this.getClass()).findAllByUser(user)).withRel("findAll"));

        // Response Entity recubre resource 
        return ResponseEntity.ok().header("X-API-VERSION", "4").body(resource);
    }

}
