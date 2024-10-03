import React, {useEffect, useState} from 'react';
import {Table, TableBody, TableCell, TableHead, TableRow, Typography, Button} from '@mui/material';
import './AccountsComponent.css';
import {User} from "../User/User";
import AccountEdit from "./AccountEdit";
import AddUserDialog from "./AddUserDialog";

interface AccountsComponentProps {
    accountsData: User[];
}

const AccountsComponent: React.FC<AccountsComponentProps> = ({accountsData}) => {
    const [selectedCustomer, setSelectedCustomer] = useState<User | null>(null);
    const [isPopUpOpen, setPopUpOpen] = useState(false);
    const [isAddDialogOpen, setAddDialogOpen] = useState(false);
    const [userList, setUserList] = useState(accountsData);

    useEffect(() => {
        fetchUserList();
    }, []);

    const handleRowClick = (customer: User) => {
        setSelectedCustomer(customer);
        setPopUpOpen(true);
    };

    const handleClosePopUp = () => {
        setSelectedCustomer(null);
        setPopUpOpen(false);
    };

    const fetchUserList = async () => {
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
                    const userWithEmail = data.find((user : any) => user.email === email);
                    if (userWithEmail) {
                        sessionStorage.setItem('userId', userWithEmail.id);
                    }

                    setUserList(data);
                } else {
                }
            }
        } catch (error) {
        }
    };

    const handleUserUpdate = () => {
        handleClosePopUp();
        fetchUserList();
    };

    const handleAddUser = () => {
        setAddDialogOpen(true);
    };

    const handleCloseAddDialog = () => {
        setAddDialogOpen(false);
    };

    return (
        <div className="accounts-container">
            <Typography variant="h5" className="accounts-title" align="center">User Accounts</Typography>
            <div className="table-container">
                <Table className="accounts-table">
                    <TableHead className="table-header">
                        <TableRow>
                            <TableCell>ID</TableCell>
                            <TableCell>Name</TableCell>
                            <TableCell>Email</TableCell>
                            <TableCell>Role</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {userList.map((user, index) => (
                            <TableRow key={user.id} onClick={() => handleRowClick(user)}>
                                <TableCell>{user.id}</TableCell>
                                <TableCell>{user.name}</TableCell>
                                <TableCell>{user.email}</TableCell>
                                <TableCell>{user.role}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </div>

            {selectedCustomer && (
                <AccountEdit
                    isOpen={isPopUpOpen}
                    onClose={handleClosePopUp}
                    customer={selectedCustomer}
                    onCustomerUpdate={handleUserUpdate}
                    onDeleteCustomer={handleUserUpdate}
                />
            )}

            <div className="button-container">
                <Button variant="contained" color="primary" onClick={handleAddUser}>
                    Add Account
                </Button>
            </div>

            {isAddDialogOpen && (
                <AddUserDialog
                    isOpen={isAddDialogOpen}
                    onClose={handleCloseAddDialog}
                    onUserAdd={fetchUserList}
                />
            )}
        </div>
    );
};

export default AccountsComponent;
