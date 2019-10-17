package com.betsapp.bets.clients;

import java.io.IOException;
import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import com.betsapp.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;

@Configuration
public class FeignConfig {

	@Bean
	public ErrorDecoder createGlobalErrorDecoder() {
		return new ErrorDecoder() {
			
			@Override
			public Exception decode(String methodKey, Response response) {
				
				if(response.status() == HttpStatus.NOT_FOUND.value()) {
					
					Collection<String> contents = response.headers().get("Content-Type");
					
					if(contents != null && 
							contents.stream().anyMatch(c -> c.contains("application/json"))) {
						
						ObjectMapper mapper = new ObjectMapper();
						try {
							JsonNode readTree = mapper.readTree(response.body().asInputStream());
							return new NotFoundException(readTree.get("message").asText());
						} catch (IOException e) {}
				    	
					}
					 
				}
				
				return new Exception("Exception not handle!");
			}
			
		};
	}
	
}
