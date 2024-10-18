import api from './api';

class UserService {
    static async login(username: string, password: string) {
        try {
            const response = await api.post('/login', { username, password });
            return response.data;
        } catch (error) {
            throw error;
        }
    }

    static async register(userData: { username: string, password: string, role: string }) {
        try {
            const response = await api.post('/register', userData);
            return response.data;
        } catch (error) {
            throw error;
        }
    }

    static logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('role');
    }

    static isAuthenticated() {
        return !!localStorage.getItem('token');
    }

    static isOwner(){
        const role = localStorage.getItem('role')
        return role === 'OWNER'
    }
}

export default UserService;
