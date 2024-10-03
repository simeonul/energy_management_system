package com.simeon.webservices.emsmonitoringcommunication.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Devices")
public class Device {
    @Id
    @GeneratedValue
    @Column(name= "id")
    private Long id;

    @Column(name= "device_id", nullable = false)
    private UUID deviceId;

    @Column(name = "max_energy_consumption", nullable = false)
    private float maxEnergyConsumption;

    @Column(name= "user_id", nullable = true)
    private UUID userId;


    public Device() {
    }

    public Device(UUID deviceId, float maxEnergyConsumption, UUID userId) {
        this.deviceId = deviceId;
        this.maxEnergyConsumption = maxEnergyConsumption;
        this.userId = userId;
    }

    public Device(Long id, UUID deviceId, float maxEnergyConsumption, UUID userId) {
        this.id = id;
        this.deviceId = deviceId;
        this.maxEnergyConsumption = maxEnergyConsumption;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public float getMaxEnergyConsumption() {
        return maxEnergyConsumption;
    }

    public void setMaxEnergyConsumption(float maxEnergyConsumption) {
        this.maxEnergyConsumption = maxEnergyConsumption;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
