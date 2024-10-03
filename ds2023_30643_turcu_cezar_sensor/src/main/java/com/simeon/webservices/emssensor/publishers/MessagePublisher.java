package com.simeon.webservices.emssensor.publishers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simeon.webservices.emssensor.configurations.Constants;
import com.simeon.webservices.emssensor.entitites.DeviceConsumption;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
public class MessagePublisher {
    private RabbitTemplate rabbitTemplate;

    @Value("${device.id}")
    private String deviceId;
    private List<Float> measurements = new ArrayList<>();
    private int currentMeasurement;

    @Autowired
    public MessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        getMeasurements();
    }

    private void getMeasurements(){
        try (Scanner scanner = new Scanner(new File("sensor.csv"))) {
            while (scanner.hasNextLine()) {
                measurements.add(Float.parseFloat(scanner.nextLine()));
            }
            currentMeasurement = 0;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void publishMessage() {
        try {
            DeviceConsumption deviceConsumption = generateDeviceConsumption();
            ObjectMapper objectMapper = new ObjectMapper();
            String queuePayload = objectMapper.writeValueAsString(deviceConsumption);
            rabbitTemplate.convertAndSend(
                    Constants.EXCHANGE,
                    Constants.ROUTING_KEY,
                    queuePayload);
            System.out.println("Message published successfully" + queuePayload);
        } catch (Exception e) {
            System.err.println("Error publishing message: " + e.getMessage());
        }
    }

    private DeviceConsumption generateDeviceConsumption(){
        ZoneId eetZone = ZoneId.of("Europe/Bucharest");
        Long currentTimestamp = Instant.now().atZone(eetZone).toEpochSecond();
        UUID deviceUuid = UUID.fromString(deviceId);
        float deviceMeasurement = measurements.get(currentMeasurement++);
        return new DeviceConsumption(currentTimestamp, deviceUuid, deviceMeasurement);
    }
}
