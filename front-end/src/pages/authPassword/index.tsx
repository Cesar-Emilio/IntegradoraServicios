import { useNavigate } from "react-router-dom";
import { useNotification } from "../../context/notification.context";
import { useEffect, useState } from "react";
import { users } from "../../api/users.api";
import { Button, Container, Paper, TextField, Typography } from "@mui/material";

export const ChangePasswordPage: React.FC<{}> = () => {
    const navigate = useNavigate();
    const { getError, getSuccess } = useNotification();
    const [currentPassword, setCurrentPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");

    useEffect(() => {
        document.title = "Cambiar Contraseña";

        const jwt = localStorage.getItem("jwt");
        const user = localStorage.getItem("user");
        if (!jwt || !user) {
            navigate("/");
        }
    }, [navigate]);

    const handleChangePassword = async () => {
        try {
            const userId = JSON.parse(localStorage.getItem("user") || "{}").userId;
            const response = await users.changePassword(userId, {
                oldPassword: currentPassword,
                newPassword: newPassword
            });
            if (response.data.type === "SUCCESS") {
                getSuccess("Contraseña actualizada correctamente");
                navigate("/profile");
            } else {
                getError("Error al actualizar la contraseña");
            }
        } catch (err) {
            console.error("Error al cambiar la contraseña:", err);
        }
    };

    return (
        <Container maxWidth="sm">
            <Paper sx={{ padding: 4 }}>
                <Typography variant="h5" gutterBottom>Cambiar Contraseña</Typography>
                <TextField
                    label="Contraseña Anterior"
                    type="password"
                    fullWidth
                    margin="normal"
                    value={currentPassword}
                    onChange={(e) => setCurrentPassword(e.target.value)}
                />
                <TextField
                    label="Nueva Contraseña"
                    type="password"
                    fullWidth
                    margin="normal"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                />
                <Button variant="contained" color="primary" onClick={handleChangePassword}>Cambiar Contraseña</Button>
            </Paper>
        </Container>
    );
}