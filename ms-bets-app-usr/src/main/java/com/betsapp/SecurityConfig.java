package com.betsapp;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.betsapp.usr.filters.JwtAuthenticationFilter;
import com.betsapp.usr.services.UserService;

// @EnableGlobalMethodSecurity(securedEnabled = true) Seguridad a nivel AOP, particular metodos pre, post
@EnableWebSecurity // Http security, mas general
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtConfig jwtConfig;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		    .csrf().disable()
	            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	            .exceptionHandling().authenticationEntryPoint(
	            		(req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
	        .and()
		    	.addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtConfig))	
		.authorizeRequests()
		    .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
		    .antMatchers("/users/**").permitAll()
		    .anyRequest().authenticated();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}
	
	@Bean
	public JwtConfig jwtConfig() {
        	return new JwtConfig();
	}

}
