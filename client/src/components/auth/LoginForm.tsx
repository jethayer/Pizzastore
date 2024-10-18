import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import UserService from '../../services/UserService';

const Login: React.FC = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const data = await UserService.login(username, password);

            const token = data.token;
            const role = data.role;

            localStorage.setItem('token', token);
            localStorage.setItem('role', role);

            if (role === 'OWNER') {
                navigate('/owner-dashboard');
            } else if (role === 'CHEF') {
                navigate('/chef-dashboard');
            } else {
                setMessage('Unknown role.');
            }
        } catch (error) {
            setMessage('Login failed. Incorrect username or password.');
        }
    };

    return (
        <div className="container">
            <h2>Login</h2>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="username" className="form-label">Username</label>
                    <input 
                        type="text" 
                        className="form-control" 
                        id="username" 
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required 
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="password" className="form-label">Password</label>
                    <input 
                        type="password" 
                        className="form-control" 
                        id="password" 
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required 
                    />
                </div>
                <button type="submit" className="btn btn-primary">Login</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};

export default Login;
