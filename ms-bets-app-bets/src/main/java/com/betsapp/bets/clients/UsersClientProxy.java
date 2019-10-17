package com.betsapp.bets.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

// @FeignClient(name="usr-ms", url="localhost:8000")
// @FeignClient(name="usr-ms")
@FeignClient(name="api-gateway")
// @RibbonClient(name="usr-ms")
public interface UsersClientProxy {
	
	// @GetMapping("/users/{id}")
	// public ResponseEntity<User> findById(@PathVariable("id") Long id);
	@GetMapping("/users/{id}")
	public User findById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);

}
