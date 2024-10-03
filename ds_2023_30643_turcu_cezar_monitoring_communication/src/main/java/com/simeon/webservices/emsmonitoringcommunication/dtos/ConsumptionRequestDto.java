package com.simeon.webservices.emsmonitoringcommunication.dtos;

import java.util.UUID;

public class ConsumptionRequestDto {
    private UUID userId;
    private Long timestamp;

    public ConsumptionRequestDto() {
    }

    public ConsumptionRequestDto(UUID userId, Long timestamp) {
        this.userId = userId;
        this.timestamp = timestamp;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
