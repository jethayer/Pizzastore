import React from 'react';
import { AppBar, Toolbar, Typography, Button } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import UserService from '../services/UserService';

const NavBar: React.FC = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    UserService.logout();
    navigate('/login');
  };

  return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6" sx={{ flexGrow: 1 }}>
          Pizza Store
        </Typography>
        {UserService.isAuthenticated() ? (
          <>
            {UserService.isOwner() && (
              <>
                <Button color="inherit" component={Link} to="/owner-dashboard">Owner Dashboard</Button>
                <Button color="inherit" component={Link} to="/chef-dashboard">Chef Dashboard</Button>
              </>
            )}
            <Button color="inherit" onClick={handleLogout}>Logout</Button>
          </>
        ) : (
          <>
            <Button color="inherit" component={Link} to="/login">Login</Button>
            <Button color="inherit" component={Link} to="/register">Register</Button>
          </>
        )}
      </Toolbar>
    </AppBar>
  );
};

export default NavBar;
