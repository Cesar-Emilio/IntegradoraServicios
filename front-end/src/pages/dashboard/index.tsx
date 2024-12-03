import React, { useEffect, useState } from "react";
import { Grid, Box, Drawer, Button, Typography } from "@mui/material";
import { proyects } from "../../api/proyects.api";
import { CardComponent } from "../../components";
import { TypeProject } from "./interface/project.interface";

export const DashboardPage: React.FC = () => {
    const [projectList, setProjectList] = useState<TypeProject[] | null>(null);

    useEffect(() => {
        proyects
            .getAll()
            .then((response) => {
                console.log(response.data.result);
                setProjectList(response.data.result);
            })
            .catch((error) => {
                console.error("Error al obtener los proyectos:", error);
            });
    }, []);

    return (
        <Box sx={{ display: "flex", flexDirection: "row", height: "100vh" }}>
            <Box sx={{ flexGrow: 1, padding: 3 }}>
                <Typography variant="h4" gutterBottom>
                    Lista de Proyectos
                </Typography>
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
        </Box>
    );
};
