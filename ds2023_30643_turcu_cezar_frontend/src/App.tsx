import './App.css';
import React from 'react';
import Login from './Login/Login';
import Register from './Register/Register';
import Admin from './Admin/Admin'
import Client from './Client/Client'
import NotAuthorized from './NotAuthorized/NotAuthorized';
import {Routes, Route} from 'react-router-dom';
import PrivateRoute from "./Configurations/PrivateRoute";
import {ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {LocalizationProvider} from '@mui/x-date-pickers';
import {AdapterDayjs} from '@mui/x-date-pickers/AdapterDayjs'

function App() {

    return (
        <div className="App">
            <ToastContainer/>
            <Routes>
                <Route path="/" element={<Login/>}/>
                <Route path="/register" element={<Register/>}/>
                <Route path="/forbidden" element={<NotAuthorized/>}/>
                <Route
                    path="/admin"
                    element={
                        <PrivateRoute element={<Admin/>} allowedRoles={['ADMIN']}/>
                    }
                />

                <Route
                    path="/client"
                    element={
                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                            <PrivateRoute element={<Client/>} allowedRoles={['CLIENT']}/>
                        </LocalizationProvider>
                    }
                />

            </Routes>

        </div>
    );
}

export default App;

