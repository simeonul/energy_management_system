import React from 'react';
import { Route, Navigate } from 'react-router-dom';

function PrivateRoute({ element, allowedRoles }: { element: React.ReactNode, allowedRoles: string[] }) {
    const storedRole = sessionStorage.getItem('authority');

    if (allowedRoles.includes(storedRole || '')) {
        return <>{element}</>;
    }

    return <Navigate to="/forbidden" />;
}

export default PrivateRoute;