package com.parking.main.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    static final String paymentQueueName = "paymentQueue";
    static final String paymentResultQueueName = "paymentResultQueue";
    static final String exchangeName = "parkingExchange";

    @Bean
    Exchange exchange() {
        return new TopicExchange(exchangeName, false, false);
    }


    @Bean
    public Queue paymentQueue() {
        return new Queue(paymentQueueName, true);
    }

    @Bean
    public Queue paymentResultQueue() {
        return new Queue(paymentResultQueueName, true);
    }


    @Bean
    public Binding paymentBinding(Queue paymentQueue, Exchange exchange) {
        return BindingBuilder.bind(paymentQueue).to(exchange).with("payment.key").noargs();
    }

    @Bean
    public Binding paymentResultBinding(Queue paymentResultQueue, Exchange exchange) {
        return BindingBuilder.bind(paymentResultQueue).to(exchange).with("payment.result.key").noargs();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
