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
import { Link, useNavigate } from "react-router-dom";
import { useNotification } from "../../context/notification.context";
import { RegisterValidate } from "../../utils/validateForm";
import { users } from "../../api/users.api";
import { TypeUser } from "../../types/user.interface";
import * as Yup from "yup";

type RegisterType = {
    email: string;
    password: string;
    name: string;
    lastname: string;
    phone: number;
};

export const RegisterPage: React.FC<{}> = () => {
    const navigate = useNavigate();
    const { getError, getSuccess } = useNotification();
    const [registerData, setRegisterData] = React.useState<RegisterType>({
        email: "",
        password: "",
        name: "",
        lastname: "",
        phone: 0,
    });

    const emailRef = React.useRef<HTMLInputElement>(null);
    const passwordRef = React.useRef<HTMLInputElement>(null);
    const nameRef = React.useRef<HTMLInputElement>(null);
    const lastnameRef = React.useRef<HTMLInputElement>(null);
    const phoneRef = React.useRef<HTMLInputElement>(null);

    useEffect(() => {
        document.title = "Regístrate";
        const jwt = localStorage.getItem("jwt");
        const user = localStorage.getItem("user");

        if (jwt && user) {
            navigate("/dashboard");
        }
    }, [navigate]);

    const dataLogin = (e: React.ChangeEvent<HTMLInputElement>) => {
        setRegisterData({ ...registerData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
    
        try {
            const phoneNumber = Number(registerData.phone);
    
            if (isNaN(phoneNumber)) {
                throw new Yup.ValidationError(
                    "El teléfono debe ser un número válido.",
                    registerData.phone,
                    "phone"
                );
            }
    
            await RegisterValidate.validate(registerData);
    
            const response = await users.create({
                name: registerData.name,
                lastname: registerData.lastname,
                email: registerData.email,
                phone: registerData.phone,
                password: registerData.password,
                admin: "ROLE_USER",
            });
    
            if (response.data.type === "SUCCESS") {
                const userData: TypeUser = response.data.result;
                localStorage.setItem("jwt", userData.jwt);
                localStorage.setItem("user", JSON.stringify(userData));
    
                getSuccess("Se ha registrado correctamente");
                navigate("/dashboard");
            } else {
                getError("Error al registrar el usuario");
            }
        } catch (error: any) {
            if (error.response) {
                if (error.response.status === 400) {
                    const errorMessage = error.response.data.message || "Error desconocido";
                    getError(errorMessage);
                } else {
                    getError(error.message);
                }
            } else {
                getError(error.message);
            }
    
            switch (error.path) {
                case "email":
                    emailRef.current?.focus();
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
                            Regístrate
                        </Typography>
                        <Box component="form" onSubmit={handleSubmit}>
                            <TextField
                                size="small"
                                name="email"
                                margin="normal"
                                type="text"
                                fullWidth
                                label="Correo electrónico"
                                inputRef={emailRef}
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
