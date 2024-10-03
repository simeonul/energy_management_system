package com.simeon.webservices.emssensor.entitites;

import java.io.Serializable;
import java.util.UUID;

public class DeviceConsumption {
    private Long timestamp;
    private UUID deviceId;
    private float measurementValue;

    public DeviceConsumption() {
    }

    public DeviceConsumption(Long timestamp, UUID deviceId, float measurementValue) {
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.measurementValue = measurementValue;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public float getMeasurementValue() {
        return measurementValue;
    }

    public void setMeasurementValue(float currentEnergyConsumption) {
        this.measurementValue = currentEnergyConsumption;
    }

    @Override
    public String toString() {
        return "DeviceConsumption{" +
                "timestamp=" + timestamp +
                ", deviceId=" + deviceId +
                ", measurementValue=" + measurementValue +
                '}';
    }
}
