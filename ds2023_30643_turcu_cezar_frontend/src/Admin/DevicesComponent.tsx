import React, {useEffect, useState} from 'react';
import {Button, Table, TableBody, TableCell, TableHead, TableRow, Typography} from '@mui/material';
import {Device} from '../Device/Device';
import './DevicesComponent.css';
import DeviceEdit from './DeviceEdit';
import AddDeviceDialog from "./AddDeviceDialog";
import {User} from "../User/User";

interface DevicesComponentProps {
    devicesData: Device[];
}

const DevicesComponent: React.FC<DevicesComponentProps> = ({devicesData}) => {
        const [selectedDevice, setSelectedDevice] = useState<Device | null>(null);
        const [isPopUpOpen, setPopUpOpen] = useState(false);
        const [isAddDialogOpen, setAddDialogOpen] = useState(false);
        const [deviceList, setDeviceList] = useState(devicesData);
        const [usersData, setUsersData] = useState<User[] | null>(null);
        const jwtToken = sessionStorage.getItem('jwt');

        useEffect(() => {
            fetchUsersData().then((data) => setUsersData(data));
        }, []);

        const fetchUsersData = async () => {
            const email = sessionStorage.getItem('email');
            const jwtToken = sessionStorage.getItem('jwt');
            try {
                if (email && jwtToken) {
                    const apiUrl = `http://localhost:8080/users`;
                    const response = await fetch(apiUrl, {
                        method: 'GET',
                        headers: {
                            'Authorization': jwtToken,
                            'Content-Type': 'application/json',
                        },
                    });

                    if (response.ok) {
                        const data = await response.json();
                        return data;
                    } else {
                    }
                }
            } catch (error) {
            }
        };

        useEffect(() => {
            fetchDeviceList();
        }, []);


        const handleRowClick = (device: Device) => {
            setSelectedDevice(device);
            setPopUpOpen(true);
        };

        const handleClosePopUp = () => {
            setSelectedDevice(null);
            setPopUpOpen(false);
        };

        const fetchDeviceList = async () => {
            if (jwtToken) {
                const apiUrl = `http://localhost:8081/devices`;
                const response = await fetch(apiUrl, {
                    method: 'GET',
                    headers: {
                        'Authorization': jwtToken,
                        'Content-Type': 'application/json',
                    },
                });

                if (response.ok) {
                    const data = await response.json();
                    setDeviceList(data);
                } else {
                }
            }
        }

        const handleDeviceUpdate = () => {
            handleClosePopUp();
            fetchDeviceList();
        };

        const handleAddDevice = () => {
            setAddDialogOpen(true);
        };

        const handleCloseAddDialog = () => {
            setAddDialogOpen(false);
        };

        return (
            <div className="devices-container">
                <Typography variant="h5" className="devices-title" align="center">
                    All Devices
                </Typography>
                <div className="table-container">
                    <Table className="devices-table">
                        <TableHead className="table-header">
                            <TableRow>
                                <TableCell>ID</TableCell>
                                <TableCell>Description</TableCell>
                                <TableCell>Address</TableCell>
                                <TableCell>Max Energy Consumption</TableCell>
                                <TableCell>User</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {deviceList.map((device, index) => (
                                <TableRow key={device.id} onClick={() => handleRowClick(device)}>
                                    <TableCell>{device.id}</TableCell>
                                    <TableCell>{device.description}</TableCell>
                                    <TableCell>{device.address}</TableCell>
                                    <TableCell>{device.maxEnergyConsumption}</TableCell>
                                    <TableCell>
                                        {usersData &&
                                            usersData.find((user) => user.id === device.userId)?.email ||
                                            'Unassigned'}
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </div>
                {selectedDevice && (
                    <DeviceEdit
                        isOpen={isPopUpOpen}
                        onClose={handleClosePopUp}
                        device={selectedDevice}
                        onDeviceUpdate={handleDeviceUpdate}
                        onDeleteDevice={handleDeviceUpdate}
                        users={[]}
                    />
                )}

                <div className="button-container">
                    <Button variant="contained" color="primary" onClick={handleAddDevice}>
                        Add Device
                    </Button>
                </div>

                {isAddDialogOpen && (
                    <AddDeviceDialog
                        isOpen={isAddDialogOpen}
                        onClose={handleCloseAddDialog}
                        onDeviceAdd={fetchDeviceList}
                    />
                )}
            </div>
        );
    }
;

export default DevicesComponent;