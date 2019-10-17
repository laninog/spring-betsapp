package com.betsapp.bets.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

// @FeignClient(name="mgr-ms", url="localhost:8100")
// @FeignClient(name="mgr-ms")
@FeignClient(name="api-gateway")
// @RibbonClient(name="mgr-ms")
public interface MatchesClientProxy {
	
	@GetMapping("/matches/{id}")
	public Match findById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);

}
