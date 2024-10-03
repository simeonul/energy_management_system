export interface Device {
    id: string;
    description: string;
    address: string;
    maxEnergyConsumption: number;
    userId: string | null;
}