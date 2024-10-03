package com.simeon.webservices.emsmonitoringcommunication.dtos;
import java.util.UUID;

public class DeviceConsumptionDto{
    private Long id;
    private Long timestamp;
    private UUID deviceId;
    private float measurementValue;

    public DeviceConsumptionDto() {
    }

    public DeviceConsumptionDto(Long timestamp, UUID deviceId, float measurementValue) {
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.measurementValue = measurementValue;
    }

    public DeviceConsumptionDto(Long id, Long timestamp, UUID deviceId, float measurementValue) {
        this.id = id;
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.measurementValue = measurementValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setMeasurementValue(float measurementValue) {
        this.measurementValue = measurementValue;
    }

    @Override
    public String toString() {
        return "DeviceConsumptionDto{" +
                "timestamp=" + timestamp +
                ", deviceId=" + deviceId +
                ", measurementValue=" + measurementValue +
                '}';
    }
}
