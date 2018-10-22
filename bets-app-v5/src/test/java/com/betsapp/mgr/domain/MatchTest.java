package com.betsapp.mgr.domain;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchTest {

	private static final String LOCAL = "local";
	
	private static Validator validator;
	
	public Match createEntity() {
		Match match = new Match();
		match.setLocal(LOCAL);
		return match;
	}
	
	@Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	
	@Test
	public void testConstraintsValidation() {
		Match match = createEntity();
		
		Set<ConstraintViolation<Match>> violations = validator.validate(match);
		
		assertThat(violations).isNotEmpty();
	}
	
}
