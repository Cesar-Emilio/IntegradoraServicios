import React from "react";
import { Card, CardContent, Typography, CardActions, Button, Divider } from "@mui/material";
import { Link } from "react-router-dom";

type ProjectProps = {
    id: number
    name: string;
    abreviation: string;
    description: string;
};

export const CardComponent: React.FC<ProjectProps> = ({ id, name, abreviation, description }) => {
    return (
        <Card sx={{ 
            minWidth: 300,
            maxWidth: 350,
            minHeight: 300,
            margin: 2, 
            display: "flex",
            flexDirection: "column",
        }}>
            <CardContent sx={{ flexGrow: 1 }}>
                <Typography variant="h5" component="div">
                    {name}
                </Typography>
                <Typography sx={{ fontSize: 14 }} color="primary" gutterBottom>
                    {abreviation}
                </Typography>
                <Divider />
                <Typography variant="body2" color="text.secondary">
                    {description}
                </Typography>
            </CardContent>
            <CardActions>
                <Link to={`/proyect/${name}/${id}`} style={{ textDecoration: "none", margin:10 }}>
                    <Button variant="contained" size="small">
                        Ver Detalles
                    </Button>
                </Link>
            </CardActions>
        </Card>
    );
};
