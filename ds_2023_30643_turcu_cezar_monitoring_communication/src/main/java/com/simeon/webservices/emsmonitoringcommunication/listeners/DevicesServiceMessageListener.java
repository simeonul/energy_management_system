package com.simeon.webservices.emsmonitoringcommunication.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simeon.webservices.emsmonitoringcommunication.configurations.Constants;
import com.simeon.webservices.emsmonitoringcommunication.dtos.DeviceDto;
import com.simeon.webservices.emsmonitoringcommunication.services.DeviceService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DevicesServiceMessageListener {

    private final DeviceService deviceService;

    @Autowired
    public DevicesServiceMessageListener(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @RabbitListener(queues = Constants.DEVICES_QUEUE)
    public void consumeMessageDevicesQueue(String payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<DeviceDto> mapType = new TypeReference<>() {};
        DeviceDto deviceDto = objectMapper.readValue(payload, mapType);
        System.out.println("Message Received from DEVICES queue: "+ deviceDto.toString());
        deviceService.dispatchCall(deviceDto);
    }
}
