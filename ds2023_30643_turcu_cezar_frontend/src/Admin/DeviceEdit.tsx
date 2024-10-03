import React, {useEffect, useState} from 'react';
import {
    Dialog,
    DialogTitle,
    DialogContent,
    TextField,
    Button,
    Select,
    MenuItem,
    FormControl,
    InputLabel
} from '@mui/material';
import {Device} from '../Device/Device';
import './DeviceEdit.css';
import {User} from "../User/User";

interface EditDialogProps {
    isOpen: boolean;
    onClose: () => void;
    device: Device | null;
    users: String[];
    onDeviceUpdate: (updatedDevice: Device) => void;
    onDeleteDevice: (deviceId: string) => void;
}

const DeviceEdit: React.FC<EditDialogProps> = ({isOpen, onClose, device, users, onDeviceUpdate, onDeleteDevice}) => {
    const [editedDescription, setEditedDescription] = useState(device?.description || '');
    const [editedAddress, setEditedAddress] = useState(device?.address || '');
    const [editedMaxEnergyConsumption, setEditedMaxEnergyConsumption] = useState(device?.maxEnergyConsumption || 0);
    const [editedUserId, setEditedUserId] = useState(device?.userId || null);
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
    const handleClose = () => {
        onClose();
    };

    const isFormValid = () => {
        return editedDescription.trim() !== '' &&
            editedAddress.trim() !== '' &&
            !isNaN(parseFloat(editedMaxEnergyConsumption.toString())) &&
            parseFloat(editedMaxEnergyConsumption.toString()) > 0;
    };

    const handleUpdate = () => {
        if (jwtToken) {
            if (device) {
                const updatedDevice: Device = {
                    ...device,
                    description: editedDescription,
                    address: editedAddress,
                    maxEnergyConsumption: editedMaxEnergyConsumption,
                    userId: editedUserId,
                };

                const apiUrl = `http://localhost:8081/devices/updateDevice/${device.id}`;
                const requestData = {
                    method: 'PUT',
                    headers: {
                        'Authorization': jwtToken,
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(updatedDevice),
                };

                fetch(apiUrl, requestData)
                    .then((response) => {
                        if (response.ok) {
                            onDeviceUpdate(updatedDevice);
                            onClose();
                        } else {
                        }
                    })
                    .catch((error) => {
                    });
            }
        }
    };

    const handleDelete = () => {
        if (device && jwtToken) {
            const apiUrl = `http://localhost:8081/devices/deleteDevice/${device.id}`;
            const requestData = {
                method: 'DELETE',
                headers: {
                    'Authorization': jwtToken,
                    'Content-Type': 'application/json',
                },
            };

            fetch(apiUrl, requestData)
                .then((response) => {
                    if (response.ok) {
                        onDeleteDevice(device.id);
                        onClose();
                    } else {
                    }
                })
                .catch((error) => {
                });
        }
    };

    return (
        <Dialog open={isOpen} onClose={handleClose}>
            <div className="title-div">
                <DialogTitle>Edit Device Information</DialogTitle>
            </div>
            <DialogContent>
                {device && (
                    <div className="device-edit-container">
                        <TextField
                            label="ID"
                            value={device.id}
                            fullWidth
                            disabled
                            className="device-edit-textfield"
                        />
                        <TextField
                            label="Description"
                            value={editedDescription}
                            fullWidth
                            onChange={(e) => setEditedDescription(e.target.value)}
                            className="device-edit-textfield"
                        />
                        <TextField
                            label="Address"
                            value={editedAddress}
                            fullWidth
                            onChange={(e) => setEditedAddress(e.target.value)}
                            className="device-edit-textfield"
                        />
                        <TextField
                            label="Max Energy Consumption"
                            value={editedMaxEnergyConsumption}
                            fullWidth
                            type="number"
                            onChange={(e) => setEditedMaxEnergyConsumption(Number(e.target.value))}
                            className="device-edit-textfield"
                        />
                        <FormControl fullWidth>
                            <InputLabel id="user-select-label">User</InputLabel>
                            <Select
                                labelId="user-select-label"
                                id="user-select"
                                value={editedUserId || ''}
                                onChange={(e) => setEditedUserId(e.target.value as string)}
                                label="User"
                            >
                                {usersData && usersData.map((user) => (
                                    <MenuItem key={user.id} value={user.id}>
                                        {user.email}
                                    </MenuItem>
                                ))}
                                <MenuItem value="">Unassigned</MenuItem>
                            </Select>
                        </FormControl>
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={handleUpdate}
                            className="device-edit-button"
                            disabled={!isFormValid()}
                        >
                            Save
                        </Button>
                        <Button variant="contained" color="error" onClick={handleDelete} className="device-edit-button">
                            Delete
                        </Button>
                    </div>
                )}
            </DialogContent>
        </Dialog>
    );
};

export default DeviceEdit;