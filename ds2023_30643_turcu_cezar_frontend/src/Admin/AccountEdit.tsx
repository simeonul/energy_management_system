import React, {useState} from 'react';
import {
    Dialog,
    DialogTitle,
    DialogContent,
    TextField,
    Button,
    MenuItem,
    Select,
    FormControl,
    InputLabel
} from '@mui/material';
import {User} from "../User/User";
import './AccountEdit.css';
import {toast} from "react-toastify";

interface EditDialogProps {
    isOpen: boolean;
    onClose: () => void;
    customer: User | null;
    onCustomerUpdate: (updatedCustomer: User) => void;
    onDeleteCustomer: (customerId: string) => void;
}

const AccountEdit: React.FC<EditDialogProps> = ({isOpen, onClose, customer, onCustomerUpdate, onDeleteCustomer}) => {
    const [editedName, setEditedName] = useState(customer?.name || '');
    const [editedEmail, setEditedEmail] = useState(customer?.email || '');
    const [editedRole, setEditedRole] = useState(customer?.role || '');
    const jwtToken = sessionStorage.getItem('jwt');
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    const handleClose = () => {
        onClose();
    };

    const isFormValid = () => {
        return editedName.trim() !== '' &&
            editedRole.trim() !== '' &&
            emailRegex.test(editedEmail);
    };

    const handleUpdate = () => {
        if (customer && jwtToken) {
            const updatedCustomer: User = {
                ...customer,
                name: editedName,
                email: editedEmail,
                role: editedRole,
            };


            const apiUrl = `http://localhost:8080/users/updateUser/${customer.id}`;
            const requestData = {
                method: 'PUT',
                headers: {
                    'Authorization': jwtToken,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(updatedCustomer),
            };

            fetch(apiUrl, requestData)
                .then((response) => {
                    if (response.ok) {
                        onCustomerUpdate(updatedCustomer);
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
                })
                .catch((error) => {
                });
        }
    };

    const handleDelete = () => {
        if (customer && jwtToken) {
            const apiUrl = `http://localhost:8080/users/deleteUser/${customer.id}`;
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
                        onDeleteCustomer(customer.id);
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
                <DialogTitle>Edit Customer Information</DialogTitle>
            </div>
            <DialogContent>
                {customer && (
                    <div className="account-edit-container">
                        <TextField
                            label="ID"
                            value={customer.id}
                            fullWidth
                            disabled
                            className="account-edit-textfield"
                        />
                        <TextField
                            label="Name"
                            value={editedName}
                            fullWidth
                            onChange={(e) => setEditedName(e.target.value)}
                            className="account-edit-textfield"
                        />
                        <TextField
                            label="Email"
                            value={editedEmail}
                            fullWidth
                            onChange={(e) => setEditedEmail(e.target.value)}
                            className="account-edit-textfield"
                        />
                        <FormControl fullWidth>
                            <InputLabel id="user-role-label">User</InputLabel>
                            <Select
                                label="Role"
                                value={editedRole}
                                onChange={(e) => setEditedRole(e.target.value as string)}
                                className="account-edit-textfield"
                            >
                                <MenuItem value="CLIENT">CLIENT</MenuItem>
                                <MenuItem value="ADMIN">ADMIN</MenuItem>
                            </Select>
                        </FormControl>
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={handleUpdate}
                            className="account-edit-button"
                            disabled={!isFormValid()}
                        >
                            Save
                        </Button>
                        <Button
                            variant="contained" color="error" onClick={handleDelete}
                                className="account-edit-button">
                            Delete
                        </Button>
                    </div>
                )}
            </DialogContent>
        </Dialog>
    );
};

export default AccountEdit;