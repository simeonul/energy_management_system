import React, {useState} from 'react';
import {
    Dialog,
    DialogTitle,
    DialogContent,
    TextField,
    Button,
    Select,
    MenuItem,
    InputLabel,
    FormControl,
    SelectChangeEvent
} from '@mui/material';
import './AddUserDialog.css';
import {toast} from "react-toastify";

interface AddUserDialogProps {
    isOpen: boolean;
    onClose: () => void;
    onUserAdd: () => void;
}

const AddUserDialog: React.FC<AddUserDialogProps> = ({isOpen, onClose, onUserAdd}) => {
    const [newUser, setNewUser] = useState({
        name: '',
        email: '',
        role: '',
        password: '',
    });

    const [emailError, setEmailError] = useState<string | null>(null);
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setNewUser({...newUser, [name]: value});
    };


    const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { value } = e.target;
        setNewUser({ ...newUser, password: value });
    };

    const handleRoleChange = (event: SelectChangeEvent) => {
        setNewUser({ ...newUser, role: event.target.value as string });
    };

    const isFormValid = () => {
        return newUser.name.trim() !== '' &&
            newUser.email.trim() !== '' &&
            newUser.role.trim() !== '' &&
            newUser.password.trim() !== '' &&
            emailRegex.test(newUser.email);
    };


    const handleAddUser = async () => {
        if (!emailRegex.test(newUser.email)) {
            setEmailError('Invalid email address!');
            return;
        }
        setEmailError(null);

        const jwtToken = sessionStorage.getItem('jwt');
        const apiUrl = 'http://localhost:8080/users';

        if (jwtToken) {
            const requestData = {
                method: 'POST',
                headers: {
                    'Authorization': jwtToken,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newUser),
            };

            try {
                const response = await fetch(apiUrl, requestData);

                if (response.ok) {
                    onUserAdd();
                    onClose();
                } else {
                    toast.error('Email already registered!', {
                        position: 'top-right',
                        autoClose: 1500,
                        hideProgressBar: false,
                        closeOnClick: true,
                        pauseOnHover: true,
                        draggable: true,
                    });
                }
            } catch (error) {
            }
        }

    };

    return (
        <Dialog open={isOpen} onClose={onClose}>
            <div className="title-div">
                <DialogTitle>Add New User</DialogTitle>
            </div>
            <DialogContent>
                <div className="add-user-dialog-content">
                    <TextField
                        name="name"
                        label="Name"
                        fullWidth
                        onChange={handleInputChange}
                        required
                    />
                    <TextField
                        name="email"
                        label="Email"
                        fullWidth
                        onChange={handleInputChange}
                        required
                        error={!!emailError}
                        helperText={emailError}
                        InputProps={{ pattern: '^[^@]+@[^@]+[^@]+$'} as any}
                    />
                    <TextField
                        type="password"
                        label="Password"
                        fullWidth
                        onChange={handlePasswordChange}
                        required
                    />
                    <FormControl fullWidth required>
                        <InputLabel id="role-label">Role</InputLabel>
                        <Select
                            labelId="role-label"
                            id="role"
                            value={newUser.role}
                            label="Role"
                            onChange={handleRoleChange}
                        >
                            <MenuItem value="CLIENT">CLIENT</MenuItem>
                            <MenuItem value="ADMIN">ADMIN</MenuItem>
                        </Select>
                    </FormControl>
                    <Button
                        variant="contained"
                        color="primary"
                        disabled={!isFormValid()}
                        onClick={handleAddUser}
                    >
                        Add User
                    </Button>
                </div>
            </DialogContent>
        </Dialog>
    );
};

export default AddUserDialog;