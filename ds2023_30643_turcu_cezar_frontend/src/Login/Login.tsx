import React, { useState, useEffect } from 'react';
import {Box, TextField, Button, Typography, InputAdornment, IconButton} from '@mui/material';
import { Visibility, VisibilityOff } from '@mui/icons-material';
import './Login.css';
import '../Register/Register.tsx';
import {Link, useNavigate} from 'react-router-dom';
import {toast} from "react-toastify";

const Login: React.FC = () => {
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [response, setResponse] = useState(null);

    const handleLogin = async () => {
        const credentials = `${username}:${password}`;
        const encodedCredentials = btoa(credentials);

        try {
            const response = await fetch('http://localhost:8080/LogIn', {
                method: 'POST',
                headers: {
                    'Authorization': `Basic ${encodedCredentials}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({}),
            });

            if (response.ok) {
                const responseData = await response.json();
                const jwtValue = response.headers.get('Authorization');
                if (jwtValue) {
                    sessionStorage.setItem('jwt', jwtValue);
                    const jwtPayload = JSON.parse(atob(jwtValue.split('.')[1]));
                    const authority = jwtPayload.authorities;
                    const email = jwtPayload.email;
                    sessionStorage.setItem('authority', authority);
                    sessionStorage.setItem('email', email);
                    if (authority === 'ADMIN') {
                        navigate('/admin');
                    } else if (authority === 'CLIENT') {
                        navigate('/client');
                    } else {
                    }
                }
            } else {
                toast.error('Wrong credentials', {
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
    };

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    const navigateToRegister = () => {
       navigate('/register');
    };


    return (
        <div className="login-page">
            <Box className="login-box">
                <Typography variant="h5" marginBottom={2}>
                    Login
                </Typography>
                <TextField
                    label="Email"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <TextField
                    label="Password"
                    type={showPassword ? 'text' : 'password'}
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    InputProps={{
                        endAdornment: (
                            <InputAdornment position="end">
                                <IconButton
                                    aria-label="toggle password visibility"
                                    onClick={togglePasswordVisibility}
                                >
                                    {showPassword ? <Visibility /> : <VisibilityOff />}
                                </IconButton>
                            </InputAdornment>
                        ),
                    }}
                />
                <Button variant="contained"
                        color="primary"
                        fullWidth
                        onClick={handleLogin}>
                    Submit
                </Button>

                <Typography variant="body1" marginTop={5} style={{ fontSize: '17px' }}>
                    If you don't have an account, you can{' '}
                    <Link to="/register" style={{ fontSize: '17px', textDecoration: 'none', color: '#3489eb' }}>
                        register here
                    </Link>
                </Typography>

            </Box>
        </div>
    );
};

export default Login;