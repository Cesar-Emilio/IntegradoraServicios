// src/hooks/useAuth.js
import { useState } from "react";
import { useAuth } from "../context/AuthContext";
import { loginUser, registerUser } from "../services/authService";

export const useAuthLogic = () => {
    const { login } = useAuth();
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleLogin = async (username, password) => {
        setLoading(true);
        setError(null);
        try {
            const userData = await loginUser(username, password); // Llamada al servicio
            login(userData); // Almacena el usuario en el contexto
        } catch (err) {
            setError("Login failed");
        }
        setLoading(false);
    };

    const handleRegister = async (userData) => {
        setLoading(true);
        setError(null);
        try {
            const newUser = await registerUser(userData);
            login(newUser); // Registra al nuevo usuario en el contexto
        } catch (err) {
            setError("Registration failed");
        }
        setLoading(false);
    };

    return { handleLogin, handleRegister, loading, error };
};
