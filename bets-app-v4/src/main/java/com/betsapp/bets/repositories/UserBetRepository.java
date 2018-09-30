package com.betsapp.bets.repositories;

import com.betsapp.bets.domain.UserBet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBetRepository extends JpaRepository<UserBet, Long> {
}
