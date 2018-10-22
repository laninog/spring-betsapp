package com.betsapp.mgr.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.betsapp.mgr.domain.Match;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MatchRepositoryTest {

	@Autowired
	private MatchRepository repository;

	private static final String LOCAL = "def_local";
	private static final String VISITOR = "def_visitor";
	private static final LocalDateTime OPEN = LocalDateTime.now();
	private static final LocalDateTime CLOSE = LocalDateTime.now();
	
	private Match createEntity() {
		Match match = new Match();
		match.setLocal(LOCAL);
		match.setVisitor(VISITOR);
		match.setOpen(OPEN);
		match.setClose(CLOSE);
		return match;
	}
	
	@Test
	public void createTest() {
		repository.saveOrUpdate(createEntity());
		
		List<Match> matches = repository.findAll();
		
		assertThat(matches).isNotEmpty();
	}
	
}
