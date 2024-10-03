package com.simeon.webservices.emsdevices.dtos.builders;

import com.simeon.webservices.emsdevices.dtos.DeviceDto;
import com.simeon.webservices.emsdevices.dtos.MonitoringDeviceDto;
import com.simeon.webservices.emsdevices.entities.Device;

public class MonitoringDeviceBuilder {

    public static MonitoringDeviceDto toMonitoringDeviceDto(Device device){
        return new MonitoringDeviceDto(
                device.getId(),
                device.getMaxEnergyConsumption(),
                device.getUserId());
    }

    public static MonitoringDeviceDto toMonitoringDeviceDto(DeviceDto device){
        return new MonitoringDeviceDto(
                device.getId(),
                device.getMaxEnergyConsumption(),
                device.getUserId());
    }
}
