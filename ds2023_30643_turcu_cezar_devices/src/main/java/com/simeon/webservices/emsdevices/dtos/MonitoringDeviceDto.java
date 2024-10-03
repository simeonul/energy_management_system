package com.simeon.webservices.emsdevices.dtos;

import java.util.UUID;

public class MonitoringDeviceDto {
    private UUID deviceId;
    private float maxEnergyConsumption;
    private UUID userId;
    private String operationType;


    public MonitoringDeviceDto() {
    }

    public MonitoringDeviceDto(UUID deviceId, float maxEnergyConsumption, UUID userId) {
        this.deviceId = deviceId;
        this.maxEnergyConsumption = maxEnergyConsumption;
        this.userId = userId;
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

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
