import axios from 'axios';

const BASE_URL = 'http://127.0.0.1:8080/';

export const instance = axios.create({
    baseURL: BASE_URL,
});
