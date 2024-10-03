package com.simeon.webservices.emsmonitoringcommunication.dtos.builders;

import com.simeon.webservices.emsmonitoringcommunication.dtos.DeviceDto;
import com.simeon.webservices.emsmonitoringcommunication.entities.Device;

public class DeviceBuilder {
    public static Device toEntity(DeviceDto deviceDto) {
        return new Device(
                deviceDto.getDeviceId(),
                deviceDto.getMaxEnergyConsumption(),
                deviceDto.getUserId()
        );
    }
}

