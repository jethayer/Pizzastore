import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { Container } from '@mui/material';
import 'bootstrap/dist/css/bootstrap.min.css';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Login from './components/auth/LoginForm';
import Register from './components/auth/RegistrationForm';
import OwnerDashboard from './components/dahsboards/OwnerDashboard';
import ChefDashboard from './components/dahsboards/ChefDashboard';
import NavBar from './components/Navbar';
import ProtectedRoute from './components/auth/ProtectedRoute';

const theme = createTheme();

const App: React.FC = () => {
  return (
    <ThemeProvider theme={theme}>
      <Router>
        <NavBar />
        <Container maxWidth="lg" sx={{ mt: 5, flexGrow: 1 }}>
          <Routes>
            <Route path="/" element={<Navigate to="/login" />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route
              path="/owner-dashboard"
              element={
                <ProtectedRoute requiredRole="OWNER">
                  <OwnerDashboard />
                </ProtectedRoute>
              }
            />
            <Route path="/chef-dashboard" element={<ChefDashboard />} />
          </Routes>
        </Container>
      </Router>
    </ThemeProvider>
  );
};

export default App;
