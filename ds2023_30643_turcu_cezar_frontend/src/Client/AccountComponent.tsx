import React from 'react';
import { User } from '../User/User';
import { Typography, Table, TableBody, TableCell, TableRow } from '@mui/material';
import './AccountComponent.css';

const AccountComponent: React.FC<{ accountData: User | null }> = ({ accountData }) => {
    return (
        <div className="account-container">
            <Typography variant="h5" className="account-title" align="center">Account Info</Typography>
            <Table className="account-table">
                <TableBody>
                    <TableRow>
                        <TableCell className="account-table-cell" align="left">ID:</TableCell>
                        <TableCell align="right">{accountData?.id}</TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell className="account-table-cell" align="left">Name:</TableCell>
                        <TableCell align="right">{accountData?.name}</TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell className="account-table-cell" align="left">Email:</TableCell>
                        <TableCell align="right">{accountData?.email}</TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell className="account-table-cell" align="left">Role:</TableCell>
                        <TableCell align="right">{accountData?.role}</TableCell>
                    </TableRow>
                </TableBody>
            </Table>
        </div>
    );
};

export default AccountComponent;