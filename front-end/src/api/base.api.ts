import axios from "axios";

export const instance = axios.create({
    baseURL: "http://localhost:8080",
    headers: {
        "Content-Type": "application/json",
    },
});

instance.interceptors.request.use((config) => {
    const token = localStorage.getItem("jwt"); 
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});
