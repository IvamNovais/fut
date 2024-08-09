import api from './api';
const autheticationService = {
    login: async (data) => {
        const response = await api.post('/auth/login', data);
        const token = response.data.token;
        const user = response.data.user;
        localStorage.setItem('token', token);
        localStorage.setItem('user', user);
        return response.data;
    },
    register: async (data) => {
        const response = await api.post('/auth/register', data);
        return response.data;
    },
    gerenciamentoCadastrados: async () => {
        const response = await api.get('/auth/gerenciamentoCadastrados');
        return response.data;
    },
    delete: async (id) => {
        const response = await api.delete(`/auth/delete/${id}`);
        return response.data;
    },
}
export default autheticationService