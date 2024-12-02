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

type RegisterType = {
    username: string;
    password: string;
    name: string;
    lastname: string;
    phone: string;
};

export const RegisterPage: React.FC<{}> = () => {
    const [registerData, setRegisterData] = React.useState<RegisterType>({
        username: "",
        password: "",
        name: "",
        lastname: "",
        phone: "",
    });

    const dataLogin = (e: React.ChangeEvent<HTMLInputElement>) => {
        setRegisterData({ ...registerData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        console.log(registerData);
        //TODO: Send data to backend
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
                        <Typography sx={{ mt: 1, mb: 1 }} variant="h4"  >
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
                                sx={{ mt: 1.5, mb: 1 }}
                                required
                                onChange={dataLogin}
                            />
                            <TextField
                                size="small"
                                name="password"
                                margin="normal"
                                type="password"
                                fullWidth
                                label="Contraseña"
                                sx={{ mt: 1.5, mb: 1 }}
                                required
                                onChange={dataLogin}
                            />

                            <TextField
                                size="small"
                                name="name"
                                margin="normal"
                                type="text"
                                fullWidth
                                label="Nombre"
                                sx={{ mt: 1.5, mb: 1 }}
                                required
                                onChange={dataLogin}
                            />
                            <TextField
                                size="small"
                                name="lastname"
                                margin="normal"
                                type="text"
                                fullWidth
                                label="Apellido"
                                sx={{ mt: 1.5, mb: 1 }}
                                required
                                onChange={dataLogin}
                            />
                            <TextField
                                size="small"
                                name="phone"
                                margin="normal"
                                type="text"
                                fullWidth
                                label="Teléfono"
                                sx={{ mt: 1.5, mb: 1 }}
                                required
                                onChange={dataLogin}
                            />

                            <Button
                                size="small"
                                fullWidth
                                type="submit"
                                variant="contained"
                                sx={{ mt: 1, mb: 1 }}
                            >
                                Iniciar sesion
                            </Button>
                            <Typography variant="body2">
                                ¿Ya tienes cuenta?{" "}
                                <Button component={Link} to="/login">Inicia sesión</Button>
                            </Typography>
                        </Box>
                    </Paper>
                </Grid>
            </Grid>
        </Container>
    );
};
