package com.betsapp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

	// public static final String exchangeName = "exchange/bets-app";

    public static final String queueName = "queue/bets-app/bets";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    /*@Bean
    TopicExchange exchange() {
    	// Topic Configuration
        return new TopicExchange(exchangeName);
    }*/

    /*@Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("new.bet");
    }*/
    
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(AccountReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
	
}
