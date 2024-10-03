package com.simeon.webservices.emsmonitoringcommunication.configurations;

import com.simeon.webservices.emsmonitoringcommunication.listeners.DevicesServiceMessageListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DevicesServiceMessagingConfig {
    @Bean
    SimpleMessageListenerContainer containerDevices(
            ConnectionFactory connectionFactory,
            @Qualifier("listenerAdapterDevices") MessageListenerAdapter listenerAdapterDevices) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(listenerAdapterDevices);
        container.setQueueNames(Constants.DEVICES_QUEUE);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapterDevices(DevicesServiceMessageListener listener) {
        return new MessageListenerAdapter(listener, "consumeMessageDevicesQueue");
    }

    @Bean
    public Queue queueDevices() {
        return new Queue(Constants.DEVICES_QUEUE);
    }

    @Bean
    public TopicExchange exchangeDevices() {
        return new TopicExchange(Constants.DEVICES_EXCHANGE);
    }

    @Bean
    public Binding bindingDevices(Queue queueDevices, TopicExchange exchangeDevices) {
        return BindingBuilder.bind(queueDevices).to(exchangeDevices).with(Constants.DEVICES_ROUTING_KEY);
    }
}
