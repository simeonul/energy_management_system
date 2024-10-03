package com.simeon.webservices.emsmonitoringcommunication.dtos;

public class ConsumptionResponseDto {
    private int hour;
    private float consumption;

    public ConsumptionResponseDto(int hour, float consumption) {
        this.hour = hour;
        this.consumption = consumption;
    }

    public ConsumptionResponseDto() {
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public float getConsumption() {
        return consumption;
    }

    public void setConsumption(float consumption) {
        this.consumption = consumption;
    }
}
