import React from 'react';
import { Box, Button, Typography } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import './NotAuthorized.css';

const NotAuthorized: React.FC = () => {
    const navigate = useNavigate();

    const handleGoToLogin = () => {
        navigate('/');
    };

    return (
        <div className="not-authorized-page">
            <Box className="not-authorized-box">
                <Typography variant="h5" marginBottom={2}>
                    You are not allowed to access this resource!
                </Typography>
                <Button
                    variant="contained"
                    color="primary"
                    fullWidth
                    onClick={handleGoToLogin}
                >
                    Take me back to the Login page
                </Button>
            </Box>
        </div>
    );
};

export default NotAuthorized;