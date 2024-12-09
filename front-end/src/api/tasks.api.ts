import { instance } from './base.api';

const endpoint = "tasks";

export const tasks = {
    create: async (data: Record<string, any>) => {
        return await instance.post(`${endpoint}/register`, data);
    },
    getAll: async (proyectId: number | undefined) => {
        return await instance.get(`${endpoint}/findAll/${proyectId}`);
    },
    get: async (id: number | undefined) => {
        return await instance.get(`${endpoint}/find/${id}`);
    },
    update: async (id: number | string, data: Record<string, any>) => {
        return await instance.put(`${endpoint}/update/${id}`, data);
    },
    changeStatus: async (id: number | string) => {
        return await instance.put(`${endpoint}/changeStatus/${id}`);
    },
    delete: async (id: number | string) => {
        return await instance.delete(`${endpoint}/delete/${id}`);
    }
};
