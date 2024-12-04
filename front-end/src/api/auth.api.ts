import { instance } from './base.api';

export const login = async (credentials: { email: string; password: string }) => {
    return await instance.post('/login', credentials);
};

export const register = async (userData: Record<string, any>) => {
    return await instance.post('/users/register', userData);
};