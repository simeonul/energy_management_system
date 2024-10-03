package com.simeon.webservices.emsdevices.dtos;

import java.util.UUID;

public class DeviceDto {

    private UUID id;
    private String description;
    private String address;
    private float maxEnergyConsumption;
    private UUID userId;

    public DeviceDto(UUID id, String description, String address, float maxEnergyConsumption, UUID userId) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.maxEnergyConsumption = maxEnergyConsumption;
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
