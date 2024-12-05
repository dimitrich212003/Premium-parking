package com.parking.main.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.main.mappers.StringToUUIDDeserializer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class RabbitMQConfiguration {
    static final String paymentQueueName = "paymentQueue";
    static final String paymentResultQueueName = "paymentResultQueue";
    static final String exchangeName = "parkingExchange";
    static final String PAYMENT_ROUTING_KEY = "payment.key";
    static final String PAYMENT_RESULT_ROUTING_KEY = "payment.result.key";


    @Bean
    Exchange exchange() {
        return new TopicExchange(exchangeName, false, false);
    }


    @Bean
    public Queue paymentQueue() {
        return new Queue(paymentQueueName, false, false, true);
    }

    @Bean
    public Queue paymentResultQueue() {
        return new Queue(paymentResultQueueName, false, false, true);
    }


    @Bean
    public Binding paymentBinding(Queue paymentQueue, Exchange exchange) {
        return BindingBuilder.bind(paymentQueue).to(exchange).with(PAYMENT_ROUTING_KEY).noargs();
    }

    @Bean
    public Binding paymentResultBinding(Queue paymentResultQueue, Exchange exchange) {
        return BindingBuilder.bind(paymentResultQueue).to(exchange).with(PAYMENT_RESULT_ROUTING_KEY).noargs();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customObjectMapper() {
        return builder -> builder.deserializerByType(UUID.class, new StringToUUIDDeserializer());
    }


}
