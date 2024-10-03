package com.simeon.webservices.emsdevices.publishers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simeon.webservices.emsdevices.configurations.Constants;
import com.simeon.webservices.emsdevices.dtos.MonitoringDeviceDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MonitoringDevicePublisher {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public MonitoringDevicePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishMessage(MonitoringDeviceDto monitoringDeviceDto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String queuePayload = objectMapper.writeValueAsString(monitoringDeviceDto);
            rabbitTemplate.convertAndSend(
                    Constants.DEVICES_EXCHANGE,
                    Constants.DEVICES_ROUTING_KEY,
                    queuePayload);
            System.out.println("Message published successfully" + queuePayload);
        } catch (Exception e) {
            System.err.println("Error publishing message: " + e.getMessage());
        }
    }
}
