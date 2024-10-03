package com.simeon.webservices.emsdevices.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Devices")
public class Device {

    @Id
    @GeneratedValue
    @Column(name= "id")
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "max_energy_consumption", nullable = false)
    private float maxEnergyConsumption;

    @Column(name= "user_id", nullable = true)
    private UUID userId;

    public Device() {
    }

    public Device(String description, String address, float maxEnergyConsumption, UUID userId) {
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
