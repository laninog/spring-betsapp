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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.betsapp.bets.services.ClientBetDTO;
import com.betsapp.bets.services.ClientBetService;

@RestController
//@RequestMapping("/api/v2")
public class ClientBetResource {

    @Autowired
    private ClientBetService userBetService;

    @PostMapping("/clients/{user}/bets")
    public ResponseEntity<ClientBetDTO> create(@PathVariable("user") Long user,
                                             @Valid @RequestBody ClientBetDTO userBet) {
		ClientBetDTO dto = userBetService.create(userBet);

		// Http 201 debe agregar una cabecera con la localizacion del nuevo recurso /api/user-bets/{id} 
		// Cambiar path a fragment si se utiliza una SPA o PWD
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
    }

    @GetMapping("/clients/{user}/bets")
    public ResponseEntity<List<Resource<ClientBetDTO>>> findAllByUser(
            @PathVariable("user") Long user) {
    	
    	// HATEOAS
    	// UserDTO podría extender de ResourceSupport (clase padre de Resource) 
    	List<Resource<ClientBetDTO>> results = 
    			userBetService.findAllByUser(user)
    				.stream().map(ub -> 
    					new Resource<>(ub, linkTo(methodOn(this.getClass()).findById(user, ub.getId())).withSelfRel())
    				).collect(Collectors.toList());
    	
    	return new ResponseEntity<>(results, HttpStatus.OK);
    }
    
    @GetMapping("/clients/{user}/bets/{id}")
    public ResponseEntity<Resource<ClientBetDTO>> findById(@PathVariable("user") Long user,
                                                         @PathVariable("id") Long id) {
        ClientBetDTO userBet = userBetService.findByIdAndUser(user, id);

        Resource<ClientBetDTO> resource = new Resource<>(userBet);

        // HATEOAS 
        resource.add(linkTo(methodOn(this.getClass()).findAllByUser(user)).withRel("findAll"));

        // Response Entity recubre resource 
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

}
