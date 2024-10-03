package com.simeon.webservices.emsmonitoringcommunication.entities;

import java.util.UUID;

public class ConsumptionMessage {
    private String deviceId;
    private float currentEnergyConsumption;
    private float maxEnergyConsumption;
    private Long timestamp;

    public ConsumptionMessage(String deviceId, float currentEnergyConsumption, float maxEnergyConsumption, Long timestamp) {
        this.deviceId = deviceId;
        this.currentEnergyConsumption = currentEnergyConsumption;
        this.maxEnergyConsumption = maxEnergyConsumption;
        this.timestamp = timestamp;
    }

    public ConsumptionMessage() {
    }


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public float getCurrentEnergyConsumption() {
        return currentEnergyConsumption;
    }

    public void setCurrentEnergyConsumption(float currentEnergyConsumption) {
        this.currentEnergyConsumption = currentEnergyConsumption;
    }

    public float getMaxEnergyConsumption() {
        return maxEnergyConsumption;
    }

    public void setMaxEnergyConsumption(float maxEnergyConsumption) {
        this.maxEnergyConsumption = maxEnergyConsumption;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ConsumptionMessage{" +
                "deviceId='" + deviceId + '\'' +
                ", currentEnergyConsumption=" + currentEnergyConsumption +
                ", maxEnergyConsumption=" + maxEnergyConsumption +
                ", timestamp=" + timestamp +
                '}';
    }
}
