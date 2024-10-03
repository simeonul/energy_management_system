import React, {useState, useEffect} from 'react';
import AccountsComponent from './AccountsComponent';
import Menu from './Menu';
import './Admin.css';
import {User} from '../User/User';
import {useNavigate} from 'react-router-dom';
import DevicesComponent from "./DevicesComponent";
import ChatComponent from "./ChatComponent";

const Admin: React.FC = () => {
        const [currentMenuItem, setCurrentMenuItem] = useState<string>('Accounts');
        const [accountsData, setAccountData] = useState<User[] | null>(null);
        const [devicesData, setDevicesData] = useState<any[] | null>(null);
        const email = sessionStorage.getItem('email');
        const jwtToken = sessionStorage.getItem('jwt');
        const navigate = useNavigate();

        useEffect(() => {
            fetchAccountData().then((data) => setAccountData(data));
        }, []);

        useEffect(() => {
            if (currentMenuItem === 'Devices' && email && jwtToken) {
                fetchDevicesData().then((data) => setDevicesData(data));
            }
        }, [currentMenuItem]);

        const handleMenuItemClick = (item: string) => {
            setCurrentMenuItem(item);
        };

        const fetchAccountData = async () => {
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


        const fetchDevicesData = async () => {
            try {
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
                        return data;
                    } else {
                    }
                }
            } catch
                (error) {
            }
        };

        const handleLogout = () => {
            sessionStorage.clear();
            navigate('/');
        };


        return (
            <div className="admin-container">
                <div className="menu-container">
                    <Menu
                        currentMenuItem={currentMenuItem}
                        handleMenuItemClick={handleMenuItemClick}
                        handleLogout={handleLogout}
                    />
                </div>
                <div className="content-container">
                    {currentMenuItem === 'Accounts' && accountsData && (
                        <AccountsComponent accountsData={accountsData}/>
                    )}
                    {currentMenuItem === 'Devices' && devicesData && (
                        <DevicesComponent devicesData={devicesData}/>
                    )}
                    {currentMenuItem === 'Chat' && (
                        <ChatComponent/>
                    )}
                </div>
            </div>
        );
    }
;

export default Admin;