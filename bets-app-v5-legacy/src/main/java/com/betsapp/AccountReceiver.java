package com.betsapp;

import org.springframework.stereotype.Component;

@Component
public class AccountReceiver {

	public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }
	
}
