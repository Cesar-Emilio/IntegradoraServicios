import React from "react";
import {
    Box,
    Button,
    Container,
    Grid,
    Paper,
    TextField,
    Typography,
} from "@mui/material";
import { Link } from "react-router-dom";
import { useNotification } from "../../context/notification.context";
import { RegisterValidate } from "../../utils/validateForm";

type RegisterType = {
    username: string;
    password: string;
    name: string;
    lastname: string;
    phone: string;
};

export const RegisterPage: React.FC<{}> = () => {
    const { getError, getSuccess } = useNotification();
    const [registerData, setRegisterData] = React.useState<RegisterType>({
        username: "",
        password: "",
        name: "",
        lastname: "",
        phone: "",
    });

    // Crear referencias para los campos
    const usernameRef = React.useRef<HTMLInputElement>(null);
    const passwordRef = React.useRef<HTMLInputElement>(null);
    const nameRef = React.useRef<HTMLInputElement>(null);
    const lastnameRef = React.useRef<HTMLInputElement>(null);
    const phoneRef = React.useRef<HTMLInputElement>(null);

    const dataLogin = (e: React.ChangeEvent<HTMLInputElement>) => {
        setRegisterData({ ...registerData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        RegisterValidate.validate(registerData)
            .then(() => {
                getSuccess("Se ha registrado correctamente");
            })
            .catch((error) => {
                getError(error.message);

                // Enfocar el campo que produjo el error
                switch (error.path) {
                    case "username":
                        usernameRef.current?.focus();
                        break;
                    case "password":
                        passwordRef.current?.focus();
                        break;
                    case "name":
                        nameRef.current?.focus();
                        break;
                    case "lastname":
                        lastnameRef.current?.focus();
                        break;
                    case "phone":
                        phoneRef.current?.focus();
                        break;
                    default:
                        break;
                }
            });
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
                            position: "relative",
                            padding: "1.2em",
                            borderRadius: "0.5em",
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
                        <Typography sx={{ mt: 1, mb: 1 }} variant="h4">
                            Regístrate
                        </Typography>
                        <Box component="form" onSubmit={handleSubmit}>
                            <TextField
                                size="small"
                                name="username"
                                margin="normal"
                                type="text"
                                fullWidth
                                label="Correo electrónico"
                                inputRef={usernameRef}
                                sx={{ mt: 1.5, mb: 1 }}
                                onChange={dataLogin}
                            />
                            <TextField
                                size="small"
                                name="password"
                                margin="normal"
                                type="password"
                                fullWidth
                                label="Contraseña"
                                inputRef={passwordRef}
                                sx={{ mt: 1.5, mb: 1 }}
                                onChange={dataLogin}
                            />
                            <TextField
                                size="small"
                                name="name"
                                margin="normal"
                                type="text"
                                fullWidth
                                label="Nombre"
                                inputRef={nameRef}
                                sx={{ mt: 1.5, mb: 1 }}
                                onChange={dataLogin}
                            />
                            <TextField
                                size="small"
                                name="lastname"
                                margin="normal"
                                type="text"
                                fullWidth
                                label="Apellido"
                                inputRef={lastnameRef}
                                sx={{ mt: 1.5, mb: 1 }}
                                onChange={dataLogin}
                            />
                            <TextField
                                size="small"
                                name="phone"
                                margin="normal"
                                type="text"
                                fullWidth
                                label="Teléfono"
                                inputRef={phoneRef}
                                sx={{ mt: 1.5, mb: 1 }}
                                onChange={dataLogin}
                            />

                            <Button
                                size="small"
                                fullWidth
                                type="submit"
                                variant="contained"
                                sx={{ mt: 1, mb: 1 }}
                            >
                                Regístrate
                            </Button>
                            <Typography variant="body2">
                                ¿Ya tienes cuenta?{" "}
                                <Button component={Link} to="/login">
                                    Inicia sesión
                                </Button>
                            </Typography>
                        </Box>
                    </Paper>
                </Grid>
            </Grid>
        </Container>
    );
};
