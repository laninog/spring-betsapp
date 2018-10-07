package com.betsapp.bets.web;

import com.betsapp.BetsAppProperties;
import com.betsapp.bets.services.UserBetDTO;
import com.betsapp.bets.services.UserBetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserBetResource {

    @Autowired
    private UserBetService userBetService;

    @PostMapping("/user-bets")
    public ResponseEntity<UserBetDTO> create(@Valid @RequestBody UserBetDTO userBet) {
        UserBetDTO userBetDTO = userBetService.create(userBet);
        ResponseEntity<UserBetDTO> res = new ResponseEntity<>(userBetDTO, HttpStatus.CREATED);
        return res;
    }

    @GetMapping("/user-bets")
    public List<UserBetDTO> findAll() {
        return userBetService.findAll();
    }

    @GetMapping("/user-bets/{id}")
    public Resource<UserBetDTO> findById(@PathVariable("id") Long id) {
        UserBetDTO userBet = userBetService.findById(id);

        Resource<UserBetDTO> resource = new Resource<>(userBet);

        // HATEOAS
        ControllerLinkBuilder link =
                ControllerLinkBuilder.linkTo(
                        ControllerLinkBuilder.methodOn(this.getClass()).findAll());

        resource.add(link.withRel("findAll"));

        return resource;
    }

}
