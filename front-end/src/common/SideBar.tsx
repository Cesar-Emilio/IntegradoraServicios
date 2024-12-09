import React, { useState, useEffect } from "react";
import {
    Paper,
    IconButton,
    Box,
    Drawer,
    Typography,
    Divider,
    List,
    ListItem,
    ListItemText,
} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import { Link, useNavigate } from "react-router-dom";
import { TypeProject } from "../types/project.interface";
import { proyects } from "../api/proyects.api";

export const SideBar: React.FC = () => {
    const navigate = useNavigate();
    const [open, setOpen] = useState(false);
    const [projectList, setProjectList] = useState<TypeProject[]>([]);

    const toggleDrawer =
        (open: boolean) => (event: React.KeyboardEvent | React.MouseEvent) => {
            setOpen(open);
        };

    const fetchProjects = async () => {
        try {
            const response = await proyects.getAll();
            setProjectList(response.data.result);
        } catch (error) {
            console.error("Error al obtener los proyectos:", error);
        }
    };

    useEffect(() => {
        fetchProjects();
    }, []);

    const handleLogout = () => {
        localStorage.removeItem("jwt");
        localStorage.removeItem("user");
        navigate("/");
    };

    const handleOptionClick = () => {
        setOpen(false);
    };

    return (
        <Box sx={{ display: "flex" }}>
            <Paper
                sx={{
                    position: "sticky",
                    top: 0,
                    width: 100,
                    height: "100vh",
                    display: "flex",
                    flexDirection: "column",
                    paddingTop: 2,
                    zIndex: 1,
                }}
                elevation={3}
            >
                <IconButton
                    sx={{ color: "black" }}
                    onClick={toggleDrawer(true)}
                >
                    <MenuIcon sx={{ color: "#DEDEDE" }} />
                </IconButton>

                <Drawer anchor="left" open={open} onClose={toggleDrawer(false)}>
                    <Box sx={{ width: 380, padding: 2 }}>
                        <Typography variant="h6" sx={{ marginBottom: 2 }}>
                            Opciones
                        </Typography>

                        <List>
                            <ListItem component={Link} to="/dashboard">
                                <ListItemText
                                    color="primary"
                                    sx={{ cursor: "pointer", color: "#DEDEDE" }}
                                    primary="Ir al inicio"
                                    onClick={handleOptionClick}
                                />
                            </ListItem>

                            <ListItem component={Link} to="/profile">
                                <ListItemText
                                    color="primary"
                                    sx={{ cursor: "pointer", color: "#DEDEDE" }}
                                    primary="Perfil"
                                    onClick={handleOptionClick}
                                />
                            </ListItem>

                            <ListItem
                                sx={{ cursor: "pointer", color: "#DEDEDE" }}
                                onClick={() => {
                                    handleLogout();
                                    handleOptionClick();
                                }}
                            >
                                <ListItemText primary="Cerrar sesión" />
                            </ListItem>

                            <Divider sx={{ marginY: 1 }} />

                            {projectList.map((project) => (
                                <ListItem
                                    component={Link}
                                    to={`/proyect/${project.name}/${project.id}`}
                                    key={project.id}
                                    onClick={handleOptionClick}
                                    color="primary"
                                    sx={{ cursor: "pointer", color: "#DEDEDE" }}
                                >
                                    <ListItemText primary={project.name} />
                                </ListItem>
                            ))}
                        </List>
                    </Box>
                </Drawer>
            </Paper>
        </Box>
    );
};
