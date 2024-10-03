package com.simeon.webservices.emsmonitoringcommunication.repositories;

import com.simeon.webservices.emsmonitoringcommunication.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, Long > {
    Optional<Device> findByDeviceId(UUID deviceId);
    void deleteByDeviceId(UUID deviceId);
    List<Device> findByUserId(UUID userId);
}
