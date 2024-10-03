import React from 'react';
import { Box, List, ListItem, ListItemText } from '@mui/material';
import './Menu.css';

interface MenuProps {
    currentMenuItem: string;
    handleMenuItemClick: (item: string) => void;
    handleLogout: () => void;
}

const Menu: React.FC<MenuProps> = ({ currentMenuItem, handleMenuItemClick, handleLogout }) => {
    return (
        <Box className="client-menu">
            <List>
                <ListItem button onClick={() => handleMenuItemClick('Account')}>
                    <ListItemText primary="My Account" />
                </ListItem>
                <ListItem button onClick={() => handleMenuItemClick('Devices')}>
                    <ListItemText primary="My Devices" />
                </ListItem>
                <ListItem button onClick={() => handleMenuItemClick('Consumption')}>
                    <ListItemText primary="Consumption" />
                </ListItem>
                <ListItem button onClick={() => handleMenuItemClick('Chat')}>
                    <ListItemText primary="Chat" />
                </ListItem>
                <ListItem button onClick={handleLogout}>
                    <ListItemText primary="Log Out" />
                </ListItem>
            </List>
        </Box>
    );
};


export default Menu;