package com.betsapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

import com.betsapp.usr.handlers.LoginSuccessHandler;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginSuccessHandler successHandler;

	@Autowired
	public void configurer(AuthenticationManagerBuilder auth) throws Exception {
		
		@SuppressWarnings("deprecation") // Test only
		UserBuilder users = User.withDefaultPasswordEncoder();

		auth.inMemoryAuthentication()
			.withUser(users.username("admin").password("admin").roles("USER", "ADMIN"))
			.withUser(users.username("user").password("user").roles("USER"));
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
