import React, { useEffect, useState, useRef } from "react";
import {
    Button,
    Container,
    Grid,
    Paper,
    Typography,
    TextField,
} from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import { useNotification } from "../../context/notification.context";
import { users } from "../../api/users.api";
import * as Yup from "yup";

const EmailValidate = Yup.object().shape({
    email: Yup.string()
        .trim()
        .email("Correo electrónico inválido")
        .required("El correo electrónico es requerido"),
});

export const PasswordSolicitudePage: React.FC = () => {
    const navigate = useNavigate();
    const { getError, getSuccess } = useNotification();
    const [email, setEmail] = useState("");
    const emailInputRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        document.title = "Cambiar contraseña";
        const jwt = localStorage.getItem("jwt");
        const user = localStorage.getItem("user");

        if (jwt && user) {
            navigate("/dashboard");
        }
    }, [navigate]);

    const handleRequestChange = async () => {
        try {
            await EmailValidate.validate({ email });
            const response = await users.requestPasswordChange(email);

            if (response.data.type === "SUCCESS") {
                getSuccess(
                    "Solicitud de cambio de contraseña enviada. Por favor, revisa tu correo."
                );
            } else {
                getError("Error al enviar la solicitud de cambio de contraseña");
            }
        } catch (err: any) {
            if (err.name === "ValidationError") {
                getError(err.message);
                emailInputRef.current?.focus();
            } else {
                console.error("Error al enviar la solicitud:", err);
                getError("Error al enviar la solicitud");
            }
        }
    };

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
                        <Button
                            component={Link}
                            to="/"
                            variant="text"
                            color="primary"
                            sx={{
                                position: "absolute",
                                top: "0.5em",
                                right: "0.5em",
                            }}
                        >
                            Volver
                        </Button>
                        <Typography sx={{ mt: 3, mb: 1 }} variant="h5" gutterBottom>
                            Solicitar Cambio de Contraseña
                        </Typography>
                        <TextField
                            label="Correo Electrónico"
                            type="email"
                            fullWidth
                            margin="normal"
                            value={email}
                            inputRef={emailInputRef}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={handleRequestChange}
                            sx={{ marginTop: 2 }}
                        >
                            Enviar Solicitud
                        </Button>
                    </Paper>
                </Grid>
            </Grid>
        </Container>
    );
};
