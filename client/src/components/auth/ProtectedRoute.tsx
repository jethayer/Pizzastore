import React from 'react';
import { Navigate } from 'react-router-dom';
import UserService from '../../services/UserService';

interface ProtectedRouteProps {
  children: JSX.Element;
  requiredRole?: string;
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ children, requiredRole }) => {
  if (!UserService.isAuthenticated()) {
    // If the user is not authenticated, redirect to login
    return <Navigate to="/login" />;
  }

  if (requiredRole && !UserService.isOwner()) {
    // If the required role is OWNER and the user is not an OWNER, redirect to login
    return <Navigate to="/login" />;
  }

  // If the user is authenticated and has the correct role, render the children component
  return children;
};

export default ProtectedRoute;
