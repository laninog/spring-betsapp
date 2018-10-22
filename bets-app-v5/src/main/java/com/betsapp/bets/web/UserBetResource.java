package com.betsapp.bets.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
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
public class UserBetResource {

    @Autowired
    private UserBetService userBetService;

    @PostMapping("/user-bets")
    public ResponseEntity<UserBetDTO> create(@Valid @RequestBody UserBetDTO userBet) {
		UserBetDTO dto = userBetService.create(userBet);

		// Http 201 debe agregar una cabecera con la localizacion del nuevo recurso /api/user-bets/{id} 
		// Cambiar path a fragment si se utiliza una SPA o PWD
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
    }

    @GetMapping(value="/user-bets", produces = "application/json")
    public ResponseEntity<List<Resource<UserBetDTO>>> findAll() {
    	
    	// HATEOAS
    	// UserDTO podría extender de ResourceSupport (clase padre de Resource) 
    	List<Resource<UserBetDTO>> results = 
    			userBetService.findAll()
    				.stream().map(ub -> 
    					new Resource<>(ub, linkTo(methodOn(this.getClass()).findById(ub.getId())).withSelfRel())
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
    
    @GetMapping("/user-bets/{id}")
    public ResponseEntity<Resource<UserBetDTO>> findById(@PathVariable("id") Long id) {
        UserBetDTO userBet = userBetService.findById(id);

        Resource<UserBetDTO> resource = new Resource<>(userBet);

        // HATEOAS 
        resource.add(linkTo(methodOn(this.getClass()).findAll()).withRel("findAll"));

        // Response Entity recubre resource 
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

}
