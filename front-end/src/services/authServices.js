// src/services/authService.js
export const loginUser = async (username, password) => {
    const response = await fetch("/api/login", {
      method: "POST",
      body: JSON.stringify({ username, password }),
      headers: { "Content-Type": "application/json" },
    });
    return await response.json();  // Respuesta con datos del usuario o token
  };
  
  export const registerUser = async (userData) => {
    const response = await fetch("/api/register", {
      method: "POST",
      body: JSON.stringify(userData),
      headers: { "Content-Type": "application/json" },
    });
    return await response.json();
  };
  