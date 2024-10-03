import React, { useState, useEffect } from 'react';
import AccountComponent from './AccountComponent';
import DevicesComponent from './DevicesComponent';
import ConsumptionComponent from './ConsumptionComponent';
import Menu from './Menu';
import './Client.css';
import { User } from '../User/User';
import {useNavigate} from 'react-router-dom';
import SockJS from "sockjs-client";
import {Stomp} from "@stomp/stompjs";
import {toast} from "react-toastify";
import ChatComponent from "./ChatComponent";


const Client: React.FC = () => {
    const [currentMenuItem, setCurrentMenuItem] = useState<string>('Account');
    const [accountData, setAccountData] = useState<User | null>(null);
    const [devicesData, setDevicesData] = useState<any[] | null>(null);
    const email = sessionStorage.getItem('email');
    const jwtToken = sessionStorage.getItem('jwt');
    const navigate = useNavigate();
    const [isConnected, setIsConnected] = useState(true);

    useEffect(() => {
        fetchAccountData().then((data) => setAccountData(data));
    }, []);

    useEffect(() => {
        if (currentMenuItem === 'Devices' && email && jwtToken) {
            fetchDevicesData().then((data) => setDevicesData(data));
        }
    }, [currentMenuItem]);

    useEffect(() => {
        if(isConnected){
            establishConnection()
        }
        return () => {
            if(stompClient && stompClient.connected){
                stompClient.disconnect();
            }
        }
    }, [isConnected])

    const handleMenuItemClick = (item: string) => {
        setCurrentMenuItem(item);
    };

    const fetchAccountData = async () => {
        try {
            if (email && jwtToken) {
                const apiUrl = `http://localhost:8080/users/getUserByEmail/${email}`;
                const response = await fetch(apiUrl, {
                    method: 'GET',
                    headers: {
                        'Authorization': jwtToken,
                        'Content-Type': 'application/json',
                    },
                });

                if (response.ok) {
                    const data = await response.json();
                    sessionStorage.setItem('userId', data.id);
                    return data;
                } else {
                }
            }
        } catch (error) {
        }
    };

    const fetchDevicesData = async () => {
        try {
            if (email && jwtToken) {
                const userId = sessionStorage.getItem('userId');
                const apiUrl = `http://localhost:8081/devices/getDevicesByUserId/${userId}`;
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

    let stompClient: any | null = null;

    const handleLogout = () => {
        if (stompClient) {
            stompClient.disconnect();
        }
        sessionStorage.clear();
        setIsConnected(false);
        navigate('/');
    };

    const establishConnection = () => {
        if (!stompClient) {
            const headers = {
                'Authorization': jwtToken,
            };

            let socket = new SockJS("http://localhost:8082/ws");
            stompClient = Stomp.over(socket);
            stompClient.connect(headers, onConnected, onError);
        }
    }

    const onConnected = () => {
        let userId = sessionStorage.getItem('userId');

        if (stompClient && stompClient.connected) {
            let connectionAddress = `/user/${userId}/private`;
            stompClient.subscribe(connectionAddress, onPrivateMessageReceived);
        }
    }

    const onPrivateMessageReceived = (payload : any) => {
        const messageData = JSON.parse(payload.body);
        const deviceId = messageData.deviceId;
        const currentEnergyConsumption = messageData.currentEnergyConsumption;
        const maxEnergyConsumption = messageData.maxEnergyConsumption;
        if (stompClient && stompClient.connected) {
            console.log("MESSAGE HAS BEEN RECEIVED!");
            const message = (
                <div>
                    Consumption exceeded for {deviceId}<br />
                    Current Consumption: {currentEnergyConsumption}<br />
                    Max Consumption: {maxEnergyConsumption}
                </div>
            );

            toast.warn(message, {
                position: toast.POSITION.TOP_RIGHT,
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true
            });
        }
    }

    const onError = () => {
        console.log("Error occured while connecting to the socket!");
    }



    return (
        <div className="client-container">
            <div className="menu-container">
                <Menu
                    currentMenuItem={currentMenuItem}
                    handleMenuItemClick={handleMenuItemClick}
                    handleLogout={handleLogout}

                />
            </div>
            <div className="content-container">
                {currentMenuItem === 'Account' && (
                    <AccountComponent accountData={accountData} />
                )}
                {currentMenuItem === 'Devices' && devicesData && (
                    <DevicesComponent devicesData={devicesData} />
                )}
                {currentMenuItem === 'Consumption' && (
                    <ConsumptionComponent />
                )}
                {currentMenuItem === 'Chat' && (
                    <ChatComponent />
                )}
            </div>
        </div>
    );
};

export default Client;