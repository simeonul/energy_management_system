package com.simeon.webservices.emsmonitoringcommunication.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simeon.webservices.emsmonitoringcommunication.configurations.Constants;
import com.simeon.webservices.emsmonitoringcommunication.dtos.DeviceConsumptionDto;
import com.simeon.webservices.emsmonitoringcommunication.services.DeviceConsumptionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class DesktopAppMessageListener {
    private Map<UUID, Integer> callCountMap = new HashMap<>();
    private Map<UUID, Float> firstConsumptionMap = new HashMap<>();

    private final DeviceConsumptionService deviceConsumptionService;

    @Autowired
    public DesktopAppMessageListener(DeviceConsumptionService deviceConsumptionService) {
        this.deviceConsumptionService = deviceConsumptionService;
    }

    @RabbitListener(queues = Constants.MONITORING_QUEUE)
    public void consumeMessageFromQueue(String payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<DeviceConsumptionDto> mapType = new TypeReference<>() {};
        DeviceConsumptionDto deviceConsumptionDto = objectMapper.readValue(payload, mapType);
        System.out.println("Message Received from MONITORING queue: "+ deviceConsumptionDto.toString());

        UUID deviceId = deviceConsumptionDto.getDeviceId();
        callCountMap.put(deviceId, callCountMap.getOrDefault(deviceId, 0) + 1);
        if(callCountMap.get(deviceId).equals(1)){
            firstConsumptionMap.put(deviceId, deviceConsumptionDto.getMeasurementValue());
        }
        if(callCountMap.get(deviceId) % 6 == 0) {
            DeviceConsumptionDto insertedDevice = new DeviceConsumptionDto(
                    deviceConsumptionDto.getTimestamp(),
                    deviceConsumptionDto.getDeviceId(),
                    deviceConsumptionDto.getMeasurementValue() - firstConsumptionMap.get(deviceId)
            );
            deviceConsumptionService.insertDeviceConsumption(insertedDevice);
            firstConsumptionMap.put(deviceId, deviceConsumptionDto.getMeasurementValue());
        }
    }
}
