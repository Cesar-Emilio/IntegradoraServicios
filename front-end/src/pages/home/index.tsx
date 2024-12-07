import React from "react";
import { Box, Button, Container, Grid, Typography } from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import { users } from "../../api/users.api";

export const HomePage: React.FC<{}> = () => {
    
    const navigate = useNavigate();

    React.useEffect(() => {
        document.title = "Inicio de sesión";
        const jwt = localStorage.getItem("jwt");
        const user = localStorage.getItem("user");

        if (jwt && user) {
            navigate("/dashboard");
        }
    }, [navigate]);

    return (
        <Container maxWidth="md">
            <Grid
                container
                direction="column"
                alignItems="center"
                justifyContent="center"
                sx={{ minHeight: "100vh", textAlign: "center" }}
            >
                <Grid item>
                    <Typography variant="h3" sx={{ mb: 2 }}>
                        Bienvenido a To-Do List
                    </Typography>
                    <Typography variant="h6" sx={{ mb: 4 }}>
                        Organiza tus tareas y mejora tu productividad
                    </Typography>
                    <Box sx={{ mb: 4 }}>
                        <img
                            src="/images/todo-list.png"
                            alt="To-Do List"
                            style={{
                                objectFit: "contain",
                                boxSizing: "border-box",
                                borderRadius: "8px",
                                maxWidth: "100%",
                                width: "300px",
                                height: "200px",
                            }}
                        />
                    </Box>
                    <Box
                        sx={{
                            display: "flex",
                            gap: 2,
                            justifyContent: "center",
                            alignItems: "center",
                        }}
                    >
                        <Button
                            component={Link}
                            to="/register"
                            variant="contained"
                            color="primary"
                        >
                            Registrarte
                        </Button>
                        <Button
                            component={Link}
                            to="/login"
                            variant="outlined"
                            color="primary"
                        >
                            Iniciar sesión
                        </Button>
                    </Box>
                </Grid>
            </Grid>
        </Container>
    );
};
