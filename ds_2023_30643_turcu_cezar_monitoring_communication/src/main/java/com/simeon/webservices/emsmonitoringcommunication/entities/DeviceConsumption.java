package com.simeon.webservices.emsmonitoringcommunication.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "devices_consumption")
public class DeviceConsumption {
    @Id
    @GeneratedValue
    @Column(name= "id")
    private Long id;

    @Column(name= "timestamp", nullable = false)
    private Long timestamp;

    @Column(name= "device_id", nullable = false)
    private UUID deviceId;

    @Column(name = "current_energy_consumption", nullable = false)
    private float measurementValue;

    public DeviceConsumption() {
    }

    public DeviceConsumption(Long timestamp, UUID deviceId, float measurementValue) {
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.measurementValue = measurementValue;
    }

    public DeviceConsumption(Long id, Long timestamp, UUID deviceId, float measurementValue) {
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

    public void setMeasurementValue(float currentEnergyConsumption) {
        this.measurementValue = currentEnergyConsumption;
    }
}
