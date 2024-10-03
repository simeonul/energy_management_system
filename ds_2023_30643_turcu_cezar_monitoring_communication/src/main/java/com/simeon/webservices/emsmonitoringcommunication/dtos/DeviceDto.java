package com.simeon.webservices.emsmonitoringcommunication.dtos;

import java.util.UUID;

public class DeviceDto {
    private Long id;
    private UUID deviceId;
    private float maxEnergyConsumption;
    private UUID userId;
    private String operationType;


    public DeviceDto() {
    }

    public DeviceDto(UUID deviceId, float maxEnergyConsumption, UUID userId, String operationType) {
        this.deviceId = deviceId;
        this.maxEnergyConsumption = maxEnergyConsumption;
        this.userId = userId;
        this.operationType = operationType;
    }

    public DeviceDto(Long id, UUID deviceId, float maxEnergyConsumption, UUID userId, String operationType) {
        this.id = id;
        this.deviceId = deviceId;
        this.maxEnergyConsumption = maxEnergyConsumption;
        this.userId = userId;
        this.operationType = operationType;
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

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        return "DeviceDto{" +
                "deviceId=" + deviceId +
                ", maxEnergyConsumption=" + maxEnergyConsumption +
                ", userId=" + userId +
                ", operationType=" + operationType +
                '}';
    }
}
