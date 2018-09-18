package com.betsapp.mgr;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.betsapp.mgr.repositories.MatchRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BetsAppV1ApplicationTests {
	
	@Autowired
	private MatchRepository repository;
	
	@Test
	public void contextLoads() {
		assertThat(repository).isNotNull();
	}

}
