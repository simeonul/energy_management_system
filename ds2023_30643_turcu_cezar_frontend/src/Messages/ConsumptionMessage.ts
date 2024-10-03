export interface ConsumptionMessage {
    deviceId: string;
    currentEnergyConsumption: number;
    maxEnergyConsumption: number;
    timestamp: number;
}