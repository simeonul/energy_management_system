import React, {useEffect, useState} from 'react';
import {
    Dialog,
    DialogTitle,
    DialogContent,
    TextField,
    Button,
    InputLabel,
    Select,
    MenuItem,
    SelectChangeEvent, FormControl
} from '@mui/material';
import './AddDeviceDialog.css';
import {User} from "../User/User";
import {NonIdDevice} from "../Device/NonIdDevice";

interface AddDeviceDialogProps {
    isOpen: boolean;
    onClose: () => void;
    onDeviceAdd: () => void;
}

const AddDeviceDialog: React.FC<AddDeviceDialogProps> = ({isOpen, onClose, onDeviceAdd}) => {
    const [newDevice, setNewDevice] = useState<NonIdDevice>({
        description: '',
        address: '',
        maxEnergyConsumption: 0.0,
        userId: '' || null,
    });

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


    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setNewDevice({...newDevice, [name]: value});
    };

    const handleUserSelectChange = (event: SelectChangeEvent) => {
        const selectedUserId = event.target.value;
        if (selectedUserId === "") {
            setNewDevice({...newDevice, userId: null});
        } else {
            setNewDevice({...newDevice, userId: selectedUserId || null});
        }
        setNewDevice({...newDevice, userId: selectedUserId || null});
    };

    const isFormValid = () => {
        return newDevice.description.trim() !== '' &&
            newDevice.address.trim() !== '' &&
            !isNaN(parseFloat(newDevice.maxEnergyConsumption.toString())) &&
            parseFloat(newDevice.maxEnergyConsumption.toString()) > 0;
    };

    const handleAddDevice = async () => {
            try {
                if (jwtToken) {
                    const apiUrl = 'http://localhost:8081/devices';
                    const response = await fetch(apiUrl, {
                        method: 'POST',
                        headers: {
                            'Authorization': jwtToken,
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(newDevice),
                    });

                    if (response.ok) {
                        onDeviceAdd();
                        onClose();
                    } else {
                    }
                }
            } catch
                (error) {
            }
        }

    return (
        <Dialog open={isOpen} onClose={onClose}>
            <div className="title-div">
                <DialogTitle>Add New Device</DialogTitle>
            </div>
            <DialogContent>
                <div className="add-device-dialog-content">
                    <TextField
                        name="description"
                        label="Description"
                        fullWidth
                        onChange={handleInputChange}
                    />
                    <TextField
                        name="address"
                        label="Address"
                        fullWidth
                        onChange={handleInputChange}
                    />
                    <TextField
                        name="maxEnergyConsumption"
                        label="Max Energy Consumption"
                        fullWidth
                        type="number"
                        onChange={handleInputChange}
                    />
                    <FormControl fullWidth>
                        <InputLabel id="user-select-label">User</InputLabel>
                        <Select
                            labelId="user-select-label"
                            id="user-select"
                            value={newDevice.userId || ""}
                            onChange={handleUserSelectChange}
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
                        disabled={!isFormValid()}
                        onClick={handleAddDevice}
                    >
                        Add Device
                    </Button>
                </div>
            </DialogContent>
        </Dialog>
    );
};

export default AddDeviceDialog;
