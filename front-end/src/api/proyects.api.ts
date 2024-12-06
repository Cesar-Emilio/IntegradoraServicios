import { instance } from './base.api';

const endpoint = "proyects";

export const proyects = {
    getAll: async () => {
        return await instance.get(`${endpoint}/findAll`);
    },
    get: async (id: number | undefined) => {
        return await instance.get(`${endpoint}/find/${id}`);
    },
    create: async (data: Record<string, any>) => {
        return await instance.post(`${endpoint}/register`, data);
    },
    update: async (id: number | string, data: Record<string, any>) => {
        return await instance.put(`${endpoint}/update/${id}`, data);
    },
    changeStatus: async (id: number | string, status: string) => {
        return await instance.put(`${endpoint}/changeStatus/${id}`, { status });
    },
    getActive: async () => {
        return await instance.get(`${endpoint}/active`);
    },
    getInactive: async () => {
        return await instance.get(`${endpoint}/inactive`);
    },
    getTasks: async (id: number | string) => {
        return await instance.get(`${endpoint}/getTasks?projectId=${id}`);
    },
};