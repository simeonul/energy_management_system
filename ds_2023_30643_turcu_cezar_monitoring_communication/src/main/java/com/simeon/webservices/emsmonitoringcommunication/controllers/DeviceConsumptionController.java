package com.simeon.webservices.emsmonitoringcommunication.controllers;


import com.simeon.webservices.emsmonitoringcommunication.dtos.ConsumptionRequestDto;
import com.simeon.webservices.emsmonitoringcommunication.dtos.ConsumptionResponseDto;
import com.simeon.webservices.emsmonitoringcommunication.entities.Device;
import com.simeon.webservices.emsmonitoringcommunication.services.DeviceConsumptionService;
import com.simeon.webservices.emsmonitoringcommunication.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device-consumption")
@CrossOrigin
public class DeviceConsumptionController {

    private final DeviceConsumptionService deviceConsumptionService;

    private final DeviceService deviceService;

    @Autowired
    public DeviceConsumptionController(DeviceConsumptionService deviceConsumptionService, DeviceService deviceService) {
        this.deviceConsumptionService = deviceConsumptionService;
        this.deviceService = deviceService;
    }

    @PostMapping
    public List<ConsumptionResponseDto> getTotalDeviceConsumptionPerDay(@RequestBody ConsumptionRequestDto consumptionRequest){
        List<Device> devicesForUser = deviceService.findByUserId(consumptionRequest.getUserId());
        return deviceConsumptionService.getTotalConsumptionsPerHours(devicesForUser, consumptionRequest.getTimestamp());
    }
}
