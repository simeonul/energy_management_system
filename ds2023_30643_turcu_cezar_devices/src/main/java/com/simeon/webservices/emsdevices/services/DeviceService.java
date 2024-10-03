package com.simeon.webservices.emsdevices.services;

import com.simeon.webservices.emsdevices.clients.UserProxy;
import com.simeon.webservices.emsdevices.dtos.DeviceDto;
import com.simeon.webservices.emsdevices.dtos.builders.DeviceBuilder;
import com.simeon.webservices.emsdevices.entities.Device;
import com.simeon.webservices.emsdevices.repositories.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;
    private final UserProxy userProxy;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, UserProxy userProxy) {
        this.deviceRepository = deviceRepository;
        this.userProxy = userProxy;
    }

    private List<UUID> getUserIds(){
        ResponseEntity<List<UUID>> proxyResponse = userProxy.getAllUserIds();
        if (proxyResponse.getStatusCode() == HttpStatus.OK) {
            return proxyResponse.getBody();
        } else {
            throw new RuntimeException("Could not perform call to getAllUsers");
        }
    }

    public DeviceDto findDeviceById(UUID id) {
        return deviceRepository.findById(id)
                .map(DeviceBuilder::toDeviceDto)
                .orElseThrow(() -> {
                    LOGGER.error("Device with id {} was not found in the database", id);
                    return new RuntimeException("Device not found");
                });
    }

    public List<DeviceDto> findAllDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDto)
                .collect(Collectors.toList());
    }

    public List<DeviceDto> findAllDeviceByUserId(UUID userId) {
        List<Device> deviceList = deviceRepository.findByUserId(userId);
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDto)
                .collect(Collectors.toList());
    }

    public Device insertDevice(DeviceDto deviceDto){
        Device device = DeviceBuilder.toEntity(deviceDto);
        device = deviceRepository.save(device);
        LOGGER.debug("Device with id {} was inserted in db", device.getId());
        return device;
    }

    public DeviceDto updateDevice(UUID deviceId, DeviceDto updatedDevice){
        Device originalDevice = deviceRepository.findById(deviceId).get();
        originalDevice.setAddress(updatedDevice.getAddress());
        originalDevice.setDescription(updatedDevice.getDescription());
        originalDevice.setMaxEnergyConsumption(updatedDevice.getMaxEnergyConsumption());
        originalDevice.setUserId(updatedDevice.getUserId());
        originalDevice = deviceRepository.save(originalDevice);
        LOGGER.debug("Device with id {} was updated in db", deviceId);
        return DeviceBuilder.toDeviceDto(originalDevice);
    }

    public void deleteDevice(UUID deviceId){
        deviceRepository.deleteById(deviceId);
    }

    public void deleteUserIds(List<DeviceDto> devices){
        for(DeviceDto device : devices){
            Device deviceEntity = DeviceBuilder.toEntity(device);
            deviceEntity.setId(device.getId());
            deviceEntity.setUserId(null);
            deviceRepository.save(deviceEntity);
        }
    }




}
