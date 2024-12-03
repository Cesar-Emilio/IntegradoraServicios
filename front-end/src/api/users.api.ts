import { instance } from './base.api';

const endpoint = "users"; 

export const users = {
    getAll: function(){
        return instance.get(`${endpoint}/findAll`);
    },
    get: function({id}: {id: number}){
        return instance.get(`${endpoint}/find/${id}`); 
    },
    create: async (data: Record<string, any>) => {
        return await instance.post(`${endpoint}/register`, data);
    },
    update: async (id: number, data: Record<string, any>) => {
        return await instance.put(`${endpoint}/update/${id}`, data);
    },
    changeStatus: async (id: number | string, status: string) => {
        return await instance.put(`${endpoint}/changeStatus/${id}`, { status });
    },
    login: async (data: { username: string; password: string }) => {
        return await instance.post(`${endpoint}/login`, data);
    },
    logout: async () => {
        return await instance.post(`${endpoint}/logout`);
    },
    changePassword: async (data: Record<string, any>) => {
        return await instance.post(`${endpoint}/changePassword`, data);
    },
    solicitudeChangePassword: async (email: string) => {
        return await instance.post(`${endpoint}/solicitudeChangePassword`, { email });
    },
    changePasswordBySolicitude: async (data: { token: string; password: string }) => {
        return await instance.post(`${endpoint}/changePasswordBySolicitude`, data);
    },
};
