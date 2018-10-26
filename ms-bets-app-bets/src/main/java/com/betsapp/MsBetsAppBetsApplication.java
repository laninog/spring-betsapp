package com.betsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsBetsAppBetsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBetsAppBetsApplication.class, args);
	}
}
