package com.simeon.webservices.emsmonitoringcommunication.repositories;

import com.simeon.webservices.emsmonitoringcommunication.entities.DeviceConsumption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeviceConsumptionRepository extends JpaRepository<DeviceConsumption, Long> {
    List<DeviceConsumption> findByDeviceIdAndTimestampBetween(UUID deviceId, Long startTime, Long endTime);
}
