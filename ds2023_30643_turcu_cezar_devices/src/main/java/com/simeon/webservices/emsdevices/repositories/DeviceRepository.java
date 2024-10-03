package com.simeon.webservices.emsdevices.repositories;

import com.simeon.webservices.emsdevices.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findByUserId(UUID uuid);
}
