package com.betsapp;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.betsapp.usr.handlers.LoginSuccessHandler;
import com.betsapp.usr.services.UserService;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginSuccessHandler successHandler;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
		
	@Autowired
	private UserService userService;
	
	@Autowired
	public void configurer(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userService)
			.passwordEncoder(passwordEncoder)
			.getUserDetailsService();
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/static/**")
				.permitAll()
			.antMatchers("/form/**")
				.hasAnyRole("ADMIN").anyRequest().authenticated()
			.antMatchers("/matches/**")
				.hasAnyRole("ADMIN", "USER").anyRequest().authenticated()
			.and()
				.formLogin()
				.successHandler(successHandler)
				.loginPage("/login")
					.permitAll()
			.and()
				.logout().permitAll()
			.and()
				.exceptionHandling();

	}

}
