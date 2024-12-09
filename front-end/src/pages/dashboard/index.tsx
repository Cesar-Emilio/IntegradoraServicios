import React, { useEffect, useState, useRef } from "react";
import AddIcon from '@mui/icons-material/Add';
import {
    Grid,
    Box,
    Drawer,
    Button,
    Typography,
    Dialog,
    DialogTitle,
    DialogContent,
    TextField,
    DialogActions,
} from "@mui/material";
import { proyects } from "../../api/proyects.api";
import { CardComponent } from "../../components";
import { useNotification } from "../../context/notification.context";
import { useNavigate } from 'react-router-dom';
import { ProyectValidate } from "../../utils/validateForm";

type TypeProject = {
    id: number;
    name: string;
    abreviation: string;
    description: string;
    status: boolean;
    user_id: number[];
};

export const DashboardPage: React.FC = () => {
    const navigate = useNavigate();

    useEffect(() => {
        document.title = "Dashboard";
        const user = localStorage.getItem("user");

        if (!user) {
            navigate("/");
        }
    }, [navigate]);

    const user = localStorage.getItem("user");
    const isAdmin = user ? JSON.parse(user).admin === "ROLE_ADMIN" : false;

    const { getError, getSuccess } = useNotification();
    const [projectList, setProjectList] = useState<TypeProject[]>([]);

    const [openProyectDialog, setOpenProyectDialog] = useState(false);
    const [proyectName, setProyectName] = useState("");
    const [proyectAbreviation, setProyectAbreviation] = useState("");
    const [proyectDescription, setProyectDescription] = useState("");

    const nameRef = useRef<HTMLInputElement>(null);
    const abreviationRef = useRef<HTMLInputElement>(null);
    const descriptionRef = useRef<HTMLInputElement>(null);


    const handleOpenProyectDialog = () => setOpenProyectDialog(true);
    const handleCloseProyectDialog = () => {
        setOpenProyectDialog(false);
        setProyectName("");
        setProyectAbreviation("");
        setProyectDescription("");
    };

    const fetchProjects = async () => {
        try {
            const response = await proyects.getAll();
            setProjectList(response.data.result);
        } catch (error) {
            getError("Error al obtener los proyectos");
            console.error("Error al obtener los proyectos:", error);
        }
    };

    const handleCreateProyect = async () => {
        try {
            await ProyectValidate.validate(
              {
                proyectName,
                proyectAbreviation,
                proyectDescription,
              },
            );
        
            const response = await proyects.create({
              name: proyectName,
              abreviation: proyectAbreviation,
              description: proyectDescription,
              user_id: [1],
            });
        
            getSuccess("Proyecto creado exitosamente");
            fetchProjects();
            handleCloseProyectDialog();
        
        } catch (error: any) {
            if (error.response) {
                getError(error.response.data.message);
            } else {
                getError(error.message);
            }

            if (error.path === "proyectName" && nameRef.current) {
                nameRef.current.focus();
            } else if (error.path === "proyectAbreviation" && abreviationRef.current) {
                abreviationRef.current.focus();
            } else if (error.path === "proyectDescription" && descriptionRef.current) {
                descriptionRef.current.focus();
            }
        }
    };

    useEffect(() => {
        fetchProjects();
    }, []);

    return (
        <Box sx={{ display: "flex", flexDirection: "row", height: "100vh" }}>
            <Box sx={{ flexGrow: 1, padding: 3 }}>
                <Box
                    sx={{
                        flexGrow: 1,
                        padding: 3,
                        display: "flex",
                        alignItems: "center",
                    }}
                >
                    <Typography variant="h4" gutterBottom sx={{ flexGrow: 1 }}>
                        Lista de Proyectos
                    </Typography>
                    {isAdmin && (
                        <Button
                            variant="contained"
                            sx={{
                                marginTop: { xs: 2, sm: 0 },
                                ml: { xs: 0, sm: 2 },
                            }}
                            startIcon={<AddIcon />}
                            onClick={handleOpenProyectDialog}
                        >
                            Agregar Proyecto
                        </Button>
                    )}
                </Box>

                {projectList && projectList.length > 0 ? (
                    <>
                        <Typography variant="body2" gutterBottom>
                            Proyectos disponibles
                        </Typography>
                        <Box
                            sx={{
                                display: "flex",
                                flexWrap: "wrap",
                                justifyContent: "flex-start",
                                gap: 2,
                            }}
                        >
                            {projectList.map((project) => (
                                <CardComponent
                                    key={project.id}
                                    id={project.id}
                                    name={project.name}
                                    abreviation={project.abreviation}
                                    description={project.description}
                                />
                            ))}
                        </Box>
                    </>
                ) : (
                    <Typography variant="body2" gutterBottom>
                        No hay proyectos disponibles
                    </Typography>
                )}
            </Box>
            <Dialog open={openProyectDialog} onClose={handleCloseProyectDialog}>
                <DialogTitle>Crear Nuevo Proyecto</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Nombre del Proyecto"
                        fullWidth
                        variant="outlined"
                        value={proyectName}
                        onChange={(e) => setProyectName(e.target.value)}
                        inputRef={nameRef}
                    />
                    <TextField
                        margin="dense"
                        label="Abreviación"
                        fullWidth
                        variant="outlined"
                        value={proyectAbreviation}
                        onChange={(e) => setProyectAbreviation(e.target.value)}
                        inputRef={abreviationRef}
                    />
                    <TextField
                        margin="dense"
                        label="Descripción"
                        fullWidth
                        variant="outlined"
                        value={proyectDescription}
                        onChange={(e) => setProyectDescription(e.target.value)}
                        inputRef={descriptionRef}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseProyectDialog} color="primary">
                        Cancelar
                    </Button>
                    <Button onClick={handleCreateProyect} color="primary">
                        Crear
                    </Button>
                </DialogActions>
            </Dialog>
        </Box>
    );
};
