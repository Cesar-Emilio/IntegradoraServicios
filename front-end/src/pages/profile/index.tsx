import React, { useEffect, useState } from "react";
import {
    Container,
    Grid,
    Paper,
    Typography,
    Button,
    Box,
    TextField,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
} from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import { users } from "../../api/users.api";
import { useNotification } from "../../context/notification.context";
import { TypeUser } from "./interface/user.interface";

interface UserDTO {
    name: string;
    lastname: string;
    email: string;
    phone: number;
    password: string;
    admin: string;
    proyect_id: number[];
    task_id: number[];
}

export const UserPage: React.FC = () => {
    const navigate = useNavigate();
    const [userData, setUserData] = useState<TypeUser>({
        id: 0,
        nombre: "",
        apellido: "",
        password: "",
        email: "",
        telefono: 0,
        status: true,
        admin: "",
        proyects: [],
        tasks: [],
    });
    const [openDialog, setOpenDialog] = useState(false);

    const { getError, getSuccess } = useNotification();

    useEffect(() => {
        document.title = "Perfil de Usuario";
        const jwt = localStorage.getItem("jwt");
        const user = localStorage.getItem("user");
        if (!jwt || !user) {
            navigate("/");
        }
        fetchUser();
    }, [navigate]);

    const fetchUser = async () => {
        const storedUser = localStorage.getItem("user");

        if (storedUser) {
            try {
                const parsedUser = JSON.parse(storedUser);
                const userId = parsedUser.userId;

                const response = await users.get({ id: userId });

                if (response.data.type === "SUCCESS") {
                    setUserData(response.data.result);
                } else {
                    getError("Error al obtener los datos del usuario");
                }
            } catch (err) {
                console.error("Error al cargar los datos:", err);
            }
        }
    };

    const handleEditClick = () => {
        setOpenDialog(true);
    };

    const handleDialogClose = () => {
        setOpenDialog(false);
    };

    const handleSave = async () => {
        try {
            const updatedUser: UserDTO = {
                name: userData.nombre,
                lastname: userData.apellido,
                email: userData.email,
                phone: userData.telefono,
                password: userData.password,
                admin: userData.admin,
                proyect_id: userData.proyects.map((p) => p.id),
                task_id: userData.tasks.map((t) => t.id),
            };

            const response = await users.update(userData.id, updatedUser);

            if (response.data.type === "SUCCESS") {
                localStorage.setItem(
                    "user",
                    JSON.stringify(response.data.result)
                );
                setOpenDialog(false);
            } else {
                getError("Error al actualizar los datos del usuario");
            }
        } catch (err) {
            console.error("Error al actualizar los datos:", err);
            fetchUser(); // Recargar datos antiguos
        }
    };

    const handleInputChange = (field: keyof TypeUser, value: any) => {
        setUserData((prev) => ({ ...prev, [field]: value }));
    };

    return (
        <Container
            maxWidth={false}
            sx={{ padding: 4, height: "100vh", width: "100vh" }}
        >
            <Grid
                container
                sx={{
                    minHeight: "100%",
                    minWidth: "100%",
                    alignItems: "center",
                    justifyContent: "center",
                }}
            >
                <Grid item xs={12}>
                    <Paper sx={{ padding: 4, width: "100%", maxWidth: 800 }}>
                        <Typography
                            variant="h4"
                            gutterBottom
                            textAlign="center"
                        >
                            Perfil
                        </Typography>

                        <Box
                            sx={{
                                display: "flex",
                                alignItems: "center",
                                marginBottom: 2,
                            }}
                        >
                            <Typography
                                variant="body1"
                                sx={{
                                    border: "1px solid #ccc",
                                    borderRadius: "10px 0 0 10px",
                                    backgroundColor: "#12181B",
                                    padding: "8px 4px",
                                    fontWeight: "bold",
                                    width: "25%",
                                    textAlign: "right",
                                    paddingRight: 2,
                                }}
                            >
                                Email:
                            </Typography>
                            <Box
                                sx={{
                                    backgroundColor: "#12181B",
                                    border: "1px solid #ccc",
                                    borderRadius: "4px",
                                    padding: "8px 12px",
                                    width: "75%",
                                }}
                            >
                                {userData?.email}
                            </Box>
                        </Box>
                        <Box>
                            <Box
                                sx={{
                                    display: "flex",
                                    alignItems: "center",
                                    marginBottom: 2,
                                }}
                            >
                                <Typography
                                    variant="body1"
                                    sx={{
                                        border: "1px solid #ccc",
                                        borderRadius: "10px 0 0 10px",
                                        backgroundColor: "#12181B",
                                        padding: "8px 4px",
                                        fontWeight: "bold",
                                        width: "25%",
                                        textAlign: "right",
                                        paddingRight: 2,
                                    }}
                                >
                                    Nombre:
                                </Typography>
                                <Box
                                    sx={{
                                        backgroundColor: "#12181B",
                                        border: "1px solid #ccc",
                                        borderRadius: "4px",
                                        padding: "8px 12px",
                                        width: "75%",
                                    }}
                                >
                                    {userData?.nombre}
                                </Box>
                            </Box>

                            <Box
                                sx={{
                                    display: "flex",
                                    alignItems: "center",
                                    marginBottom: 2,
                                }}
                            >
                                <Typography
                                    variant="body1"
                                    sx={{
                                        border: "1px solid #ccc",
                                        borderRadius: "10px 0 0 10px",
                                        backgroundColor: "#12181B",
                                        padding: "8px 4px",
                                        fontWeight: "bold",
                                        width: "25%",
                                        textAlign: "right",
                                        paddingRight: 2,
                                    }}
                                >
                                    Apellido:
                                </Typography>
                                <Box
                                    sx={{
                                        backgroundColor: "#12181B",
                                        border: "1px solid #ccc",
                                        borderRadius: "0 10px 10px 0",
                                        padding: "8px 12px",
                                        width: "75%",
                                    }}
                                >
                                    {userData?.apellido}
                                </Box>
                            </Box>

                            <Box
                                sx={{
                                    display: "flex",
                                    alignItems: "center",
                                    marginBottom: 2,
                                }}
                            >
                                <Typography
                                    variant="body1"
                                    sx={{
                                        border: "1px solid #ccc",
                                        borderRadius: "10px 0 0 10px",
                                        backgroundColor: "#12181B",
                                        padding: "8px 4px",
                                        fontWeight: "bold",
                                        width: "25%",
                                        textAlign: "right",
                                        paddingRight: 2,
                                    }}
                                >
                                    Teléfono:
                                </Typography>
                                <Box
                                    sx={{
                                        backgroundColor: "#12181B",
                                        border: "1px solid #ccc",
                                        borderRadius: "4px",
                                        padding: "8px 12px",
                                        width: "75%",
                                    }}
                                >
                                    {userData?.telefono}
                                </Box>
                            </Box>

                            <Box
                                sx={{
                                    display: "flex",
                                    alignItems: "center",
                                    marginBottom: 2,
                                }}
                            >
                                <Typography
                                    variant="body1"
                                    sx={{
                                        fontWeight: "bold",
                                        width: "25%",
                                        textAlign: "right",
                                        paddingRight: 2,
                                    }}
                                >
                                    Estado:
                                </Typography>
                                <Box
                                    sx={{
                                        borderRadius: "4px",
                                        padding: "8px 12px",
                                        width: "75%",
                                        color:
                                            userData?.status === true
                                                ? "success.main"
                                                : "error.main",
                                    }}
                                >
                                    {userData?.status ? "Activo" : "Inactivo"}
                                </Box>
                            </Box>
                            <Typography
                                variant="body1"
                                sx={{
                                    fontWeight: "bold",
                                    width: "100%",
                                    textAlign: "left",
                                    paddingX: 2,
                                }}
                                color="primary"
                                component={Link}
                                to="/changePassword"
                            >
                                Olvidaste tu contraseña?
                            </Typography>
                        </Box>

                        <Box
                            sx={{
                                marginTop: 4,
                                display: "flex",
                                justifyContent: "space-between",
                            }}
                        >
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={() => navigate("/dashboard")}
                            >
                                Volver
                            </Button>
                            <Button
                                variant="contained"
                                color="secondary"
                                onClick={handleEditClick}
                            >
                                Editar Datos
                            </Button>
                        </Box>
                    </Paper>
                </Grid>
            </Grid>
            <Dialog open={openDialog} onClose={handleDialogClose}>
                <DialogTitle>Editar Datos del Usuario</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Nombre"
                        fullWidth
                        variant="standard"
                        value={userData?.nombre || ""}
                        onChange={(e) =>
                            handleInputChange("nombre", e.target.value)
                        }
                    />
                    <TextField
                        margin="dense"
                        label="Apellido"
                        fullWidth
                        variant="standard"
                        value={userData?.apellido || ""}
                        onChange={(e) =>
                            handleInputChange("apellido", e.target.value)
                        }
                    />
                    <TextField
                        margin="dense"
                        label="Teléfono"
                        fullWidth
                        variant="standard"
                        value={userData?.telefono || ""}
                        onChange={(e) =>
                            handleInputChange("telefono", e.target.value)
                        }
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleDialogClose} color="primary">
                        Cancelar
                    </Button>
                    <Button onClick={handleSave} color="primary">
                        Guardar
                    </Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
};
