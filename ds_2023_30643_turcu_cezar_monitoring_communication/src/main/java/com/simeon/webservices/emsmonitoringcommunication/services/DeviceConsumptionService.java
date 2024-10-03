package com.simeon.webservices.emsmonitoringcommunication.services;

import com.simeon.webservices.emsmonitoringcommunication.controllers.WebSocketController;
import com.simeon.webservices.emsmonitoringcommunication.dtos.ConsumptionResponseDto;
import com.simeon.webservices.emsmonitoringcommunication.dtos.DeviceConsumptionDto;
import com.simeon.webservices.emsmonitoringcommunication.dtos.builders.DeviceConsumptionBuilder;
import com.simeon.webservices.emsmonitoringcommunication.entities.ConsumptionMessage;
import com.simeon.webservices.emsmonitoringcommunication.entities.Device;
import com.simeon.webservices.emsmonitoringcommunication.entities.DeviceConsumption;
import com.simeon.webservices.emsmonitoringcommunication.repositories.DeviceConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class DeviceConsumptionService {

    private final DeviceConsumptionRepository deviceConsumptionRepository;

    private final DeviceService deviceService;

    private final WebSocketController webSocket;

    @Autowired
    public DeviceConsumptionService(
            DeviceConsumptionRepository deviceConsumptionRepository,
            DeviceService deviceService,
            WebSocketController webSocket) {
        this.deviceConsumptionRepository = deviceConsumptionRepository;
        this.deviceService = deviceService;
        this.webSocket = webSocket;
    }


    public void insertDeviceConsumption(DeviceConsumptionDto deviceConsumption) {
        Optional<Device> repositoryDevice = deviceService.findByDeviceId(deviceConsumption.getDeviceId());

        if (repositoryDevice.isPresent()) {
            Device deviceEntity = repositoryDevice.get();
            if (deviceEntity.getUserId() != null) {
                deviceConsumptionRepository.save(DeviceConsumptionBuilder.toEntity(deviceConsumption));
                if (deviceEntity.getMaxEnergyConsumption() < deviceConsumption.getMeasurementValue()) {
                    System.out.println("CONSUMPTION HAS BEEN EXCEEDED");
                    ConsumptionMessage message = new ConsumptionMessage(
                            deviceEntity.getDeviceId().toString(),
                            deviceConsumption.getMeasurementValue(),
                            deviceEntity.getMaxEnergyConsumption(),
                            deviceConsumption.getTimestamp()
                    );
                    webSocket.sendNewPrivateMessage(message, deviceEntity.getUserId().toString());
                }
            }
        }
    }

    public List<ConsumptionResponseDto> getTotalConsumptionsPerHours(List<Device> devices, Long timestamp) {
        List<ConsumptionResponseDto> consumptionsPerHour = new ArrayList<>();
        List<Long> timestampsForDay = getTimestampsForDay(timestamp);
        if (devices.size() > 0) {
            for (int i = 0; i < timestampsForDay.size() - 1; i++) {
                float totalConsumption = 0.0f;
                for (Device device : devices) {
                    List<DeviceConsumption> consumptionsForDeviceBetweenHours =
                            deviceConsumptionRepository.findByDeviceIdAndTimestampBetween(
                                    device.getDeviceId(),
                                    timestampsForDay.get(i),
                                    timestampsForDay.get(i + 1));
                    for (DeviceConsumption deviceConsumption : consumptionsForDeviceBetweenHours) {
                        totalConsumption += deviceConsumption.getMeasurementValue();
                    }
                }
                consumptionsPerHour.add(
                        new ConsumptionResponseDto(
                                i,
                                totalConsumption
                        )
                );
            }
            return consumptionsPerHour;
        }
        return new ArrayList<>();
    }

    private List<Long> getTimestampsForDay(Long timestamp) {
        List<Long> timestampsForDay = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            long currentTimestamp = (timestamp / (3600 * 24)) * (3600 * 24) + i * 3600 - 2 * 3600;
            timestampsForDay.add(currentTimestamp);
        }
        return timestampsForDay;
    }
}
