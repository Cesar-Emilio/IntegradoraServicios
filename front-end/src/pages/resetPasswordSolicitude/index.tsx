import React, { useEffect, useState } from "react";
import { useNavigate, useParams, useSearchParams } from "react-router-dom";
import * as Yup from "yup";
import { Container, Grid, Paper, Typography, TextField, Button } from "@mui/material";
import { useNotification } from "../../context/notification.context";
import { PasswordValidate } from "../../utils/validateForm";
import { users } from "../../api/users.api";

export const ResetPasswordSolicitudePage: React.FC = () => {
    const [searchParams] = useSearchParams();
    const token = searchParams.get("token");
    const navigate = useNavigate();
    const { getError, getSuccess } = useNotification();
    const [password, setPassword] = useState<string>("");
    const [confirmPassword, setConfirmPassword] = useState<string>("");
    const passwordInputRef = React.useRef<HTMLInputElement>(null);
    const confirmPasswordInputRef = React.useRef<HTMLInputElement>(null);

    const handleResetPassword = async () => {
        if (!token) {
            getError("Token no encontrado");
            return;
        }
        try {
            await PasswordValidate.validate({ password, confirmPassword });

            const response = await users.resetPassword({ token, password });

            if (response.data.type === "SUCCESS") {
                getSuccess("Contraseña restablecida con éxito. Ahora puedes iniciar sesión.");
                navigate("/login");
            } else {
                getError("Error al restablecer la contraseña");
            }
        } catch (err: any) {
            if (err.name === "ValidationError") {
                getError(err.message);

                if (err.path === "password") {
                    passwordInputRef.current?.focus();
                } else if (err.path === "confirmPassword") {
                    confirmPasswordInputRef.current?.focus();
                }
            } else {
                console.error("Error al restablecer la contraseña:", err);
                getError("Error inesperado al restablecer la contraseña");
            }
        }
    };

    useEffect(() => {
        document.title = "Restablecer Contraseña";
        
        const jwt = localStorage.getItem("jwt");
        const user = localStorage.getItem("user");

        if (jwt && user) {
            navigate("/dashboard");
        }
    }, [navigate]);

    return (
        <Container maxWidth="sm">
            <Grid
                container
                direction="column"
                alignItems="center"
                justifyContent="center"
                sx={{ minHeight: "100vh" }}
            >
                <Grid item>
                    <Paper
                        sx={{
                            padding: 4,
                            position: "relative",
                        }}
                    >
                        <Typography sx={{ mt: 3, mb: 1 }} variant="h5" gutterBottom>
                            Restablecer Contraseña
                        </Typography>
                        <TextField
                            label="Nueva Contraseña"
                            type="password"
                            fullWidth
                            margin="normal"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            inputRef={passwordInputRef}
                        />
                        <TextField
                            label="Confirmar Contraseña"
                            type="password"
                            fullWidth
                            margin="normal"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            inputRef={confirmPasswordInputRef}
                        />
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={handleResetPassword}
                            sx={{ marginTop: 2 }}
                        >
                            Cambiar Contraseña
                        </Button>
                    </Paper>
                </Grid>
            </Grid>
        </Container>
    );
};