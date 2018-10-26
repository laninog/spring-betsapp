package com.betsapp.bets.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betsapp.bets.domain.ClientBet;

public interface ClientBetRepository extends JpaRepository<ClientBet, Long> {

    Optional<ClientBet> findByIdAndClient(Long id, Long client);

}
