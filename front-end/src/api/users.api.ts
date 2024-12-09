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
    logout: async () => {
        return await instance.post(`${endpoint}/logout`);
    },
    changePassword: async (userId: number, data: { oldPassword: string, newPassword: string }) => {
        return await instance.put(`${endpoint}/changePassword/${userId}`, data);
    },
    requestPasswordChange: async (email: string) => {
        return await instance.post(`${endpoint}/solicitudeChangePassword/${encodeURIComponent(email)}`);
    },    
    changePasswordBySolicitude: async (data: { token: string; password: string }) => {
        return await instance.post(`${endpoint}/changePasswordBySolicitude`, data);
    },
};
