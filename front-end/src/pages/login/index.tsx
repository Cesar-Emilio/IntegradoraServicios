import React, { useEffect } from "react";
import {
    Box,
    Button,
    Container,
    Grid,
    Paper,
    TextField,
    Typography,
} from "@mui/material";
import { login as authLogin } from "../../api/auth.api";
import { Link, useNavigate } from "react-router-dom";
import { useNotification } from "../../context/notification.context";
import { LoginValidate } from "../../utils/validateForm";
import { TypeUser } from "../../types/user.interface";

type LoginType = {
    email: string;
    password: string;
};

export const LoginPage: React.FC<{}> = () => {
    const { getError, getSuccess } = useNotification();
    const [loginData, setLoginData] = React.useState<LoginType>({
        email: "",
        password: "",
    });

    const navigate = useNavigate();
    const usernameRef = React.useRef<HTMLInputElement>(null);
    const passwordRef = React.useRef<HTMLInputElement>(null);

    useEffect(() => {
        const jwt = localStorage.getItem("jwt");
        const user = localStorage.getItem("user");

        if (jwt && user) {
            navigate("/dashboard");
        }
    }, [navigate]);

    const dataLogin = (e: React.ChangeEvent<HTMLInputElement>) => {
        setLoginData({ ...loginData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        try {
            await LoginValidate.validate(loginData);

            const response = await authLogin(loginData);

            const userData: TypeUser = response.data.result;
            console.log(userData);
            if (userData && userData.jwt) {
                localStorage.setItem("jwt", userData.jwt);
                localStorage.setItem("user", JSON.stringify(userData));

                getSuccess("Inicio de sesión exitoso");
                navigate("/dashboard");
            } else {
                getError("Datos de usuario incorrectos");
            }

            getSuccess("Inicio de sesión exitoso");
            navigate("/dashboard");
        } catch (error: any) {
            if (error.response) {
                getError(error.response.data.message);
            } else {
                getError(error.message);
            }

            if (error.path === "username" && usernameRef.current) {
                usernameRef.current.focus();
            } else if (error.path === "password" && passwordRef.current) {
                passwordRef.current.focus();
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
                            Iniciar sesión
                        </Typography>
                        <Box component="form" onSubmit={handleSubmit}>
                            <TextField
                                size="small"
                                name="email"
                                margin="normal"
                                type="text"
                                fullWidth
                                label="Correo electrónico"
                                inputRef={usernameRef}
                                sx={{ mt: 2, mb: 1 }}
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
                                sx={{ mt: 1, mb: 1.5 }}
                                onChange={dataLogin}
                            />

                            <Button
                                size="small"
                                fullWidth
                                type="submit"
                                variant="contained"
                                sx={{ mt: 1.5, mb: 1 }}
                            >
                                Iniciar sesión
                            </Button>
                            <Typography variant="body2">
                                ¿No tienes cuenta?{" "}
                                <Button component={Link} to="/register">
                                    Regístrate
                                </Button>
                            </Typography>
                        </Box>
                    </Paper>
                </Grid>
            </Grid>
        </Container>
    );
};
