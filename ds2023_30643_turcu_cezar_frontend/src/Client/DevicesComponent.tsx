import React from 'react';
import {Table, TableBody, TableCell, TableHead, TableRow, Typography} from '@mui/material';
import '../Device/Device'
import {Device} from "../Device/Device";
import './DevicesComponent.css';

interface DevicesComponentProps {
    devicesData: Device[];
}


const DevicesComponent: React.FC<DevicesComponentProps> = ({devicesData}) => {
    return (
        <div className="devices-container">
            <Typography variant="h5" className="devices-title" align="center">My Devices</Typography>
            <div className="table-container">
                <Table className="devices-table">
                    <TableHead className="table-header">
                        <TableRow>
                            <TableCell>ID</TableCell>
                            <TableCell>Description</TableCell>
                            <TableCell>Address</TableCell>
                            <TableCell>Max Energy Consumption</TableCell>
                            <TableCell>User ID</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {devicesData.map((device, index) => (
                            <TableRow key={device.id}>
                                <TableCell>{device.id}</TableCell>
                                <TableCell>{device.description}</TableCell>
                                <TableCell>{device.address}</TableCell>
                                <TableCell>{device.maxEnergyConsumption}</TableCell>
                                <TableCell>{device.userId}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </div>

        </div>
    );
};

export default DevicesComponent;