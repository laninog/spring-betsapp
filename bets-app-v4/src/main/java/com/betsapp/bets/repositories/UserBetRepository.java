package com.betsapp.bets.repositories;

import com.betsapp.bets.domain.UserBet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBetRepository extends JpaRepository<UserBet, Long> {

    Optional<UserBet> findByIdAndUserId(Long id, Long userId);

}
