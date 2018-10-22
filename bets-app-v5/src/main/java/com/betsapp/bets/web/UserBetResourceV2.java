package com.betsapp.bets.web;

import com.betsapp.MessagingConfig;
import com.betsapp.bets.services.UserBetDTO;
import com.betsapp.bets.services.UserBetService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
@RequestMapping("/api/v2")
public class UserBetResourceV2 {

    @Autowired
    private UserBetService userBetService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/users/{user}/bets")
    public ResponseEntity<UserBetDTO> create(@PathVariable("user") Long user,
                                             @Valid @RequestBody UserBetDTO userBet) {
		UserBetDTO dto = userBetService.create(userBet);

		// Http 201 debe agregar una cabecera con la localizacion del nuevo recurso /api/user-bets/{id} 
		// Cambiar path a fragment si se utiliza una SPA o PWD
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
    }

    @PostMapping("/users/bets")
    public ResponseEntity<UserBetDTO> create(@Valid @RequestBody UserBetDTO userBet) {

        rabbitTemplate.convertAndSend(MessagingConfig.exchangeName, "new.bet", userBet.toString());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}/processed")
                .buildAndExpand(userBet.getUser())
                .toUri();

        return ResponseEntity.accepted().location(uri).body(userBet);
    }

    @GetMapping("/users/{user}/bets")
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
    
    @GetMapping("/user/{user}/bets/{id}")
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
