package com.simeon.webservices.emsdevices.dtos.builders;

import com.simeon.webservices.emsdevices.dtos.DeviceDto;
import com.simeon.webservices.emsdevices.entities.Device;


public class DeviceBuilder {

    public static DeviceDto toDeviceDto(Device device){
        return new DeviceDto(
                device.getId(),
                device.getDescription(),
                device.getAddress(),
                device.getMaxEnergyConsumption(),
                device.getUserId());
    }

    public static Device toEntity(DeviceDto deviceDto){
        return new Device(deviceDto.getDescription(),
                deviceDto.getAddress(),
                deviceDto.getMaxEnergyConsumption(),
                deviceDto.getUserId());
    }
}
