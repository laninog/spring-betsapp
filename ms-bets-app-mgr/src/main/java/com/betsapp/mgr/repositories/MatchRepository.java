package com.betsapp.mgr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betsapp.mgr.domain.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {
	
}
