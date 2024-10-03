package com.simeon.webservices.emsmonitoringcommunication.configurations;

import com.simeon.webservices.emsmonitoringcommunication.listeners.DesktopAppMessageListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DesktopAppMessagingConfig {

    @Bean
    SimpleMessageListenerContainer containerMonitoring(
            ConnectionFactory connectionFactory,
            @Qualifier("listenerAdapterMonitoring") MessageListenerAdapter listenerAdapterMonitoring) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(listenerAdapterMonitoring);
        container.setQueueNames(Constants.MONITORING_QUEUE);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapterMonitoring(DesktopAppMessageListener listener) {
        return new MessageListenerAdapter(listener, "consumeMessageFromQueue");
    }

    @Bean
    public Queue queueMonitoring() {
        return new Queue(Constants.MONITORING_QUEUE);
    }

    @Bean
    public TopicExchange exchangeMonitoring() {
        return new TopicExchange(Constants.EXCHANGE);
    }

    @Bean
    public Binding bindingMonitoring(
            Queue queueMonitoring,
            TopicExchange exchangeMonitoring) {
        return BindingBuilder.bind(queueMonitoring).to(exchangeMonitoring).with(Constants.ROUTING_KEY);
    }
}
