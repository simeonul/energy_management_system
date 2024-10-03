package com.simeon.webservices.emsmonitoringcommunication.dtos.builders;

import com.simeon.webservices.emsmonitoringcommunication.dtos.DeviceConsumptionDto;
import com.simeon.webservices.emsmonitoringcommunication.entities.DeviceConsumption;

public class DeviceConsumptionBuilder {
    public static DeviceConsumptionDto toDeviceConsumptionDto(DeviceConsumption device) {
        return new DeviceConsumptionDto(
                device.getId(),
                device.getTimestamp(),
                device.getDeviceId(),
                device.getMeasurementValue()
        );
    }

    public static DeviceConsumption toEntity(DeviceConsumptionDto deviceDto) {
        return new DeviceConsumption(
                deviceDto.getTimestamp(),
                deviceDto.getDeviceId(),
                deviceDto.getMeasurementValue()
        );
    }
}
