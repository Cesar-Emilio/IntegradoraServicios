import React, { useState, useEffect } from "react";
import {
    Box,
    Typography,
    Button,
    List,
    ListItem,
    ListItemText,
    Divider,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    TextField,
    MenuItem,
    Select,
    InputLabel,
    FormControl,
} from "@mui/material";
import { useParams } from "react-router-dom";

const mockCategories = [
    { id: 1, name: "Desarrollo" },
    { id: 2, name: "Testing" },
    { id: 3, name: "Documentación" },
];

const mockUsers = [
    { id: 1, name: "cesar" },
    { id: 2, name: "ander" },
    { id: 3, name: " erikito" },
];

const exampleTasks = [
    { id: 1, name: "Tarea 1", description: "Descripción de la tarea 1" },
    { id: 2, name: "Tarea 2", description: "Descripción de la tarea 2" },
    { id: 3, name: "Tarea 3", description: "Descripción de la tarea 3" },
];

export const ProjectPage: React.FC = () => {
    const { projectId } = useParams<{ projectId: string }>();
    
    const [openTaskDialog, setOpenTaskDialog] = useState(false);
    const [taskName, setTaskName] = useState("");
    const [taskDescription, setTaskDescription] = useState("");
    const [selectedCategory, setSelectedCategory] = useState<number | "">("");
    const [selectedUser, setSelectedUser] = useState<number | "">("");
    
    const [openCategoryDialog, setOpenCategoryDialog] = useState(false);
    const [categoryName, setCategoryName] = useState("");
    const [categoryDescription, setCategoryDescription] = useState("");

    const handleOpenTaskDialog = () => setOpenTaskDialog(true);
    const handleOpenCategoryDialog = () => setOpenCategoryDialog(true);

    const handleCloseTaskDialog = () => {
        setOpenTaskDialog(false);
        setTaskName(""); 
        setTaskDescription("");
        setSelectedCategory("");
        setSelectedUser("");
    };

    const handleCloseCategoryDialog = () => {
        setOpenCategoryDialog(false);
        setCategoryName("");
        setCategoryDescription("");
    };

    const handleCreateTask = () => {
        //TODO: Llamada a la api de creación de tareas
        //TODO: Hacer un alert o notificación de que se ha creado la tarea
        console.log("Tarea creada:", taskName, taskDescription, selectedCategory, selectedUser);
        handleCloseTaskDialog();
    };

    const handleCreateCategory = () => {
        //TODO: Llamada a la api de creación de categorías
        //TODO: Hacer un alert o notificación de que se ha creado la categoría
        console.log("Categoria creada:", categoryName, categoryDescription);
        handleCloseCategoryDialog();
    }

    //TODO: Llamada a la api de obtencion de categorías
    useEffect(() => {
    }, [projectId]);

    //TODO: Llamada a la api de obtencion de usuarios
    useEffect(() => {
    }, [projectId]);

    return (
        <Box sx={{ padding: 3 }}>
            <Box
                sx={{
                    display: "flex",
                    alignItems: "center",
                    marginBottom: 3,
                    flexWrap: "wrap",
                }}
            >
                <Box
                    sx={{ display: "flex", alignItems: "center", flexGrow: 1 }}
                >
                    <Typography
                        variant="h4"
                        component="h1"
                        sx={{ marginRight: 2 }}
                    >
                        Nombre del Proyecto
                    </Typography>
                    <Typography
                        variant="h6"
                        color="primary"
                        sx={{ marginRight: 2 }}
                    >
                        PIA
                    </Typography>
                </Box>
                <Button
                    variant="contained"
                    sx={{
                        marginTop: { xs: 2, sm: 0 },
                        mr: { xs: 0, sm: 2 },
                    }}
                    onClick={handleOpenCategoryDialog}
                >
                    Agregar Categoria
                </Button>
                <Button
                    variant="contained"
                    sx={{
                        marginTop: { xs: 2, sm: 0 },
                    }}
                    onClick={handleOpenTaskDialog}
                >
                    Agregar Tarea
                </Button>
            </Box>

            <Typography variant="h6" sx={{ marginBottom: 2 }}>
                Tareas del Proyecto
            </Typography>
            <List>
                {exampleTasks.map((task) => (
                    <ListItem key={task.id}>
                        <ListItemText
                            primary={task.name}
                            secondary={task.description}
                        />
                    </ListItem>
                ))}
            </List>
            <Divider />

            <Dialog open={openTaskDialog} onClose={handleCloseTaskDialog}>
                <DialogTitle>Crear Nueva Tarea</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Nombre de la Tarea"
                        fullWidth
                        variant="outlined"
                        value={taskName}
                        onChange={(e) => setTaskName(e.target.value)}
                    />
                    <TextField
                        margin="dense"
                        label="Descripción"
                        fullWidth
                        variant="outlined"
                        value={taskDescription}
                        onChange={(e) => setTaskDescription(e.target.value)}
                    />

                    <FormControl fullWidth margin="dense">
                        <InputLabel id="category-select-label">Categoría</InputLabel>
                        <Select
                            labelId="category-select-label"
                            value={selectedCategory}
                            onChange={(e) => setSelectedCategory(Number(e.target.value) || "")}
                            label="Categoría"
                        >
                            {mockCategories.map((category) => (
                                <MenuItem key={category.id} value={category.id}>
                                    {category.name}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>

                    <FormControl fullWidth margin="dense">
                        <InputLabel id="user-select-label">Responsable</InputLabel>
                        <Select
                            labelId="user-select-label"
                            value={selectedUser}
                            onChange={(e) => setSelectedUser(Number(e.target.value) || "")}
                            label="Responsable"
                        >
                            {mockUsers.map((user) => (
                                <MenuItem key={user.id} value={user.id}>
                                    {user.name}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseTaskDialog} color="primary">
                        Cancelar
                    </Button>
                    <Button onClick={handleCreateTask} color="primary">
                        Crear
                    </Button>
                </DialogActions>
            </Dialog>

            <Dialog open={openCategoryDialog} onClose={handleCloseCategoryDialog}>
                <DialogTitle>Crear Nueva Categoría</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Nombre de la Categoría"
                        fullWidth
                        variant="outlined"
                        value={categoryName}
                        onChange={(e) => setCategoryName(e.target.value)}
                    />
                    <TextField
                        margin="dense"
                        label="Descripción"
                        fullWidth
                        variant="outlined"
                        value={categoryDescription}
                        onChange={(e) => setCategoryDescription(e.target.value)}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseCategoryDialog} color="primary">
                        Cancelar
                    </Button>
                    <Button onClick={handleCreateCategory} color="primary">
                        Crear
                    </Button>
                </DialogActions>
            </Dialog>
        </Box>
    );
};
