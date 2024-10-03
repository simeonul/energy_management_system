package com.simeon.webservices.emsdevices.services;

import com.simeon.webservices.emsdevices.dtos.DeviceDto;
import com.simeon.webservices.emsdevices.dtos.MonitoringDeviceDto;
import com.simeon.webservices.emsdevices.dtos.builders.MonitoringDeviceBuilder;
import com.simeon.webservices.emsdevices.entities.Device;
import com.simeon.webservices.emsdevices.publishers.MonitoringDevicePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoringDeviceService {
    private final String INSERT = "INSERT";
    private final String UPDATE = "UPDATE";
    private final String DELETE = "DELETE";
    private final String DELETE_USER_ID = "DELETE_USER_ID";
    private final MonitoringDevicePublisher publisher;

    @Autowired
    public MonitoringDeviceService(MonitoringDevicePublisher publisher) {
        this.publisher = publisher;
    }

    public void insertDevice(Device device) {
        MonitoringDeviceDto monitoringDeviceDto = MonitoringDeviceBuilder.toMonitoringDeviceDto(device);
        monitoringDeviceDto.setOperationType(INSERT);
        publisher.publishMessage(monitoringDeviceDto);
    }

    public void updateDevice(DeviceDto device) {
        MonitoringDeviceDto monitoringDeviceDto = MonitoringDeviceBuilder.toMonitoringDeviceDto(device);
        monitoringDeviceDto.setOperationType(UPDATE);
        publisher.publishMessage(monitoringDeviceDto);
    }

    public void deleteDevice(DeviceDto device) {
        MonitoringDeviceDto monitoringDeviceDto = MonitoringDeviceBuilder.toMonitoringDeviceDto(device);
        monitoringDeviceDto.setOperationType(DELETE);
        publisher.publishMessage(monitoringDeviceDto);
    }

    public void deleteUserIds(List<DeviceDto> devices){
        for(DeviceDto device : devices){
            MonitoringDeviceDto monitoringDeviceDto = MonitoringDeviceBuilder.toMonitoringDeviceDto(device);
            monitoringDeviceDto.setOperationType(DELETE_USER_ID);
            publisher.publishMessage(monitoringDeviceDto);
        }
    }
}
