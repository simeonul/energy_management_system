package com.simeon.webservices.emsmonitoringcommunication.services;

import com.simeon.webservices.emsmonitoringcommunication.dtos.DeviceDto;
import com.simeon.webservices.emsmonitoringcommunication.dtos.builders.DeviceBuilder;
import com.simeon.webservices.emsmonitoringcommunication.entities.Device;
import com.simeon.webservices.emsmonitoringcommunication.entities.DeviceConsumption;
import com.simeon.webservices.emsmonitoringcommunication.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;


    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public void dispatchCall(DeviceDto deviceDto){
        switch (deviceDto.getOperationType()){
            case "INSERT" -> insertDevice(deviceDto);
            case "UPDATE" -> updateDevice(deviceDto);
            case "DELETE" -> deleteDevice(deviceDto);
            case "DELETE_USER_ID" -> deleteUserId(deviceDto);
            default -> {
                return;
            }
        }
    }

    public void insertDevice(DeviceDto deviceDto){
        deviceRepository.save(DeviceBuilder.toEntity(deviceDto));
    }

    public void updateDevice(DeviceDto deviceDto){
        Device originalDevice = deviceRepository.findByDeviceId(deviceDto.getDeviceId()).get();
        originalDevice.setUserId(deviceDto.getUserId());
        originalDevice.setMaxEnergyConsumption(deviceDto.getMaxEnergyConsumption());
        deviceRepository.save(originalDevice);
    }

    public void deleteDevice(DeviceDto deviceDto){
        Device deviceEntity = deviceRepository.findByDeviceId(deviceDto.getDeviceId()).get();
        deviceRepository.deleteById(deviceEntity.getId());
    }

    public void deleteUserId(DeviceDto deviceDto){
        Device deviceEntity = DeviceBuilder.toEntity(deviceDto);
        Device originalDevice = deviceRepository.findByDeviceId(deviceDto.getDeviceId()).get();
        deviceEntity.setId(originalDevice.getId());
        deviceEntity.setUserId(null);
        deviceRepository.save(deviceEntity);
    }

    public Optional<Device> findByDeviceId(UUID deviceId){
        return deviceRepository.findByDeviceId(deviceId);
    }

    public List<Device> findByUserId(UUID userId){
        return deviceRepository.findByUserId(userId);
    }
}
