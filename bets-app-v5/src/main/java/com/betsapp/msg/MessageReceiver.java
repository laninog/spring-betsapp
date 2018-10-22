package com.betsapp.msg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    private static final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);

    public void receive(String msg) {
        logger.info("Message received " + msg);
    }

}
