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
    private ClientBetService clientBetService;

    @PostMapping("/clients/{client}/bets")
    public ResponseEntity<ClientBetDTO> create(@PathVariable("client") Long client,
                                             @Valid @RequestBody ClientBetDTO clientBet) {
		ClientBetDTO dto = clientBetService.create(clientBet);

		// Http 201 debe agregar una cabecera con la localizacion del nuevo recurso /api/client-bets/{id} 
		// Cambiar path a fragment si se utiliza una SPA o PWD
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
    }

    @GetMapping("/clients/{client}/bets")
    public ResponseEntity<List<Resource<ClientBetDTO>>> findAllByClient(
            @PathVariable("client") Long client) {
    	
    	// HATEOAS
    	// UserDTO podría extender de ResourceSupport (clase padre de Resource) 
    	List<Resource<ClientBetDTO>> results = 
    			clientBetService.findAllByClient(client)
    				.stream().map(ub -> 
    					new Resource<>(ub, linkTo(methodOn(this.getClass()).findById(client, ub.getId())).withSelfRel())
    				).collect(Collectors.toList());
    	
    	return new ResponseEntity<>(results, HttpStatus.OK);
    }
    
    @GetMapping("/clients/{client}/bets/{id}")
    public ResponseEntity<Resource<ClientBetDTO>> findById(@PathVariable("client") Long client,
                                                         @PathVariable("id") Long id) {
        ClientBetDTO clientBet = clientBetService.findByIdAndClient(client, id);

        Resource<ClientBetDTO> resource = new Resource<>(clientBet);

        // HATEOAS 
        resource.add(linkTo(methodOn(this.getClass()).findAllByClient(client)).withRel("findAll"));

        // Response Entity recubre resource 
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

}
