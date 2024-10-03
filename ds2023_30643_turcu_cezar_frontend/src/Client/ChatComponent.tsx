import React, {useEffect, useRef, useState} from "react";
import './ChatComponent.css';
import SockJS from "sockjs-client";
import {Stomp} from "@stomp/stompjs";
import {User} from "../User/User";
import {List, ListItemButton, ListItemText, Box, Stack, Input, IconButton} from "@mui/material";


const ChatComponent: React.FC = () => {
    const [isConnected, setIsConnected] = useState(false);
    const [privateMessages, setPrivateMessages] = useState(new Map());
    const [accountsData, setAccountData] = useState<User[] | null>(null);
    const email = sessionStorage.getItem('email');
    const jwtToken = sessionStorage.getItem('jwt');
    const userId = sessionStorage.getItem('userId');
    const [tab, setTab] = useState('');
    const chatStompClient = useRef<any | null>(null);
    const [userMessage, setUserMessage] = useState('');
    const [isTyping, setIsTyping] = useState(false);
    const messageListRef = useRef<HTMLUListElement>(null);


    useEffect(() => {
        establishConnection();
        return () => {
            if (chatStompClient.current && chatStompClient.current.connected) {
                chatStompClient.current.disconnect();
            }
        };
    }, []);

    const establishConnection = () => {
        if (!chatStompClient.current) {
            const headers = {
                'Authorization': jwtToken,
            };
            let socket = new SockJS("http://localhost:8083/chatws");
            chatStompClient.current = Stomp.over(socket);
            chatStompClient.current.connect(headers, onConnected);
        }
    };

    const onConnected = () => {
        if (chatStompClient && chatStompClient.current.connected) {
            setIsConnected(true);
            let connectionAddress = `/user/${userId}/private`;
            console.log(connectionAddress);
            chatStompClient.current.subscribe(connectionAddress, onPrivateMessageReceived);

            fetchAccountData().then((data) => {
                setAccountData(data);
            });
        }
    }


    const onPrivateMessageReceived = (payload: any) => {
        const messageData = JSON.parse(payload.body);
        if (messageData.type === "TYPING") {
            setIsTyping(true);
            setTimeout(() => {
                setIsTyping(false);
            }, 2000);
        }
        else{
            if (privateMessages.get(messageData.senderId)) {
                privateMessages.get(messageData.senderId).push(messageData);
                setPrivateMessages(new Map(privateMessages));
            } else {
                let messagesList = [];
                messagesList.push(messageData);
                privateMessages.set(messageData.senderId, messagesList);
                setPrivateMessages(new Map(privateMessages));
            }
        }
    }

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

    const handleListItemClick = (
        event: React.MouseEvent<HTMLDivElement, MouseEvent>,
        index: string,
    ) => {
        setTab(index);
        if (!isConnected) {
            establishConnection()
        }
    };


    const sendPrivateMessage = () => {
        console.log("IN SEND")
        const trimmedMessage = userMessage.trim();
        if (chatStompClient && trimmedMessage !== '') {
            let chatMessage = {
                senderId: userId,
                receiverId: tab,
                content: userMessage,
                type: "CONTENT"
            };
            if (chatMessage.receiverId !== '') {
                if (privateMessages.get(chatMessage.receiverId)) {
                    privateMessages.get(chatMessage.receiverId).push(chatMessage);
                    setPrivateMessages(new Map(privateMessages));
                } else {
                    let messagesList = [];
                    messagesList.push(chatMessage);
                    privateMessages.set(chatMessage.receiverId, messagesList);
                    setPrivateMessages(new Map(privateMessages));
                }

                chatStompClient.current.send("/chat/private-chat", {}, JSON.stringify(chatMessage));
                setUserMessage("");
            }
        }
    }

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setUserMessage(e.target.value);
        sendTypingMessage();
    }

    const sendTypingMessage = () => {
        if (chatStompClient) {
            let typingMessage = {
                senderId: userId,
                receiverId: tab,
                type: "TYPING"
            };
            if (typingMessage.receiverId !== '') {
                chatStompClient.current.send("/chat/private-chat", {}, JSON.stringify(typingMessage));
            }
        }
    }

    useEffect(() => {
        if (messageListRef.current) {
            messageListRef.current.scrollTop = messageListRef.current.scrollHeight;
        }
    }, [privateMessages]);


    return (
        <div className="chat-container">
            {isConnected ?
                <div className="chat-area">
                    <div className="user-list-container">
                        <List component={Stack} direction="row">
                            {accountsData &&
                                accountsData
                                    .filter((user) => user.role === "ADMIN")
                                    .map((adminUser) => (
                                        <ListItemButton
                                            key={adminUser.id}
                                            selected={tab === adminUser.id}
                                            sx={{
                                                height: 40,
                                                borderRadius: 3
                                            }}
                                            onClick={(event) => handleListItemClick(event, adminUser.id)}
                                        >
                                            <ListItemText primary={adminUser.name}/>
                                        </ListItemButton>
                                    ))}
                        </List>
                    </div>

                    <Box
                        className="message-box-container"
                        sx={{
                            border: 3,
                            marginTop: 1,
                            borderRadius: 3,
                            borderColor: '#3489eb'
                        }}
                    >
                        <List
                            className="message-list"
                            ref={messageListRef}
                            style={{
                                maxHeight: '93%',
                                overflow: 'auto'
                            }}
                        >
                            {privateMessages.get(tab)?.map((message: any, index: any) => (
                                <ListItemText
                                    style={{display: 'flex',
                                        flexDirection: 'row',
                                        justifyContent: message.senderId === userId ? 'flex-end' : 'flex-start',
                                        backgroundColor: message.senderId === userId ? '#ffffff' : '#d3e0ea',
                                        padding: '8px',
                                        margin: '4px',
                                        borderRadius: '8px',
                                    }}
                                    key={index}
                                    primary={message.content}
                                />
                            ))}
                            {isTyping && (
                                <div style=
                                         {{
                                             margin: '8px',
                                             color: '#555',
                                             fontSize: '14px'
                                         }}>
                                    Typing...
                                </div>
                            )}
                        </List>
                    </Box>

                    <Box
                        sx={{
                            display: 'flex',
                            alignItems: 'center',
                            marginTop: 1
                        }}>
                        <Input
                            placeholder="Type your message..."
                            value={userMessage}
                            onChange={handleInputChange}
                            sx={{
                                flex: 1,
                                marginRight: 1,
                                fontSize: 17
                            }}
                        />
                        <IconButton
                            onClick={sendPrivateMessage}
                            sx={{
                                color: '#3489eb',
                                fontSize: 20,
                                borderRadius: 3
                            }}
                        >
                            Send
                        </IconButton>
                    </Box>

                </div> :
                <div className="error on connection">
                </div>
            }
        </div>
    );
}
export default ChatComponent;