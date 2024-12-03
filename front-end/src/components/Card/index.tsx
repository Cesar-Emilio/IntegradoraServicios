import React from "react";
import { Card, CardContent, Typography, CardActions, Button, Divider } from "@mui/material";
import { Link } from "react-router-dom";

type ProjectProps = {
    name: string;
    abreviation: string;
    description: string;
};

export const CardComponent: React.FC<ProjectProps> = ({ name, abreviation, description }) => {
    return (
        <Card sx={{ maxWidth: 345, margin: 2 }}>
            <CardContent>
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
                <Link to={`/proyect/${name}`} style={{ textDecoration: "none" }}>
                    <Button variant="contained" size="small">
                        Ver Detalles
                    </Button>
                </Link>
            </CardActions>
        </Card>
    );
};
