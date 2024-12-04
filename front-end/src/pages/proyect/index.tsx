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
import { useNavigate, useParams } from "react-router-dom";
import { proyects } from "../../api/proyects.api";
import { categories } from "../../api/categories.api";
import { useNotification } from "../../context/notification.context";
import { tasks } from "../../api/tasks.api";
import { users } from "../../api/users.api";

type TypeCategory = {
    name: string;
    description: string;
    proyect_id: number;
    status: boolean;
};

type TypeTask = {
    name: string;
    description: string;
    category_id: number;
    proyect_id: number;
    responsibles_id: number[];
};

export const ProjectPage: React.FC = () => {
    const navigate = useNavigate();
    const { getError, getSuccess } = useNotification();

    useEffect(() => {
        const user = localStorage.getItem("user");

        if (!user) {
            navigate("/");
        }
    }, [navigate]);

    const { nombreProyecto, id } = useParams<{
        nombreProyecto: string;
        id: string;
    }>();
    const proyect_id = id ? Number(id) : undefined;

    const user = localStorage.getItem("user");
    const isAdmin = user ? JSON.parse(user).admin === "ROLE_ADMIN" : false;

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

    const [listCategories, setListCategories] = useState<any[]>([]);
    const [listUsers, setListUsers] = useState<any[]>([]);

    const fetchUsers = async () => {
        if (!proyect_id) return;

        try {
            const response = await users.getAll();
            console.log("Usuarios obtenidos:", response);
            setListUsers(response.data.result);
        } catch (error) {
            console.error("Error al obtener los usuarios:", error);
        }
    };

    const fetchCategories = async () => {
        if (!proyect_id) return;

        try {
            const response = await categories.getAll(proyect_id);
            console.log("Categorías obtenidas:", response);
            setListCategories(response.data.result);
        } catch (error) {
            console.error("Error al obtener las categorías:", error);
        }
    };

    useEffect(() => {
        if (proyect_id) {
            fetchCategories();
            fetchUsers();
        }
    }, [proyect_id]);

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

    const handleCreateTask = async () => {
        if (
            !taskName ||
            !taskDescription ||
            !selectedCategory ||
            !selectedUser
        ) {
            getError("Todos los campos son obligatorios.");
            return;
        }
        const taskData: TypeTask = {
            name: taskName,
            description: taskDescription,
            category_id: selectedCategory,
            proyect_id: Number(proyect_id),
            responsibles_id: [parseInt(String(selectedUser))]
        };

        try {
            console.log(taskData.responsibles_id);
            const response = await tasks.create(taskData);
            getSuccess("Tarea creada exitosamente");

            handleCloseTaskDialog();
        } catch (error) {
            getError("Error al crear la tarea");
        }
    };

    const handleCreateCategory = async () => {
        if (!categoryName || !categoryDescription) {
            getError("Todos los campos son obligatorios.");
            return;
        }

        const categoryData: TypeCategory = {
            name: categoryName,
            description: categoryDescription,
            proyect_id: Number(proyect_id),
            status: true,
        };

        console.log("Creando categoría:", categoryData);

        try {
            const response = await categories.create(categoryData);
            console.log("Categoría creada exitosamente:", response);
            getSuccess("Categoría creada exitosamente");

            fetchCategories();

            handleCloseCategoryDialog();
        } catch (error) {
            console.error("Error al crear la categoría:", error);
        }
    };

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

                {isAdmin && (
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
                )}

                {isAdmin && (
                    <Button
                        variant="contained"
                        sx={{
                            marginTop: { xs: 2, sm: 0 },
                        }}
                        onClick={handleOpenTaskDialog}
                    >
                        Agregar Tarea
                    </Button>
                )}
            </Box>

            <Typography variant="h6" sx={{ marginBottom: 2 }}>
                Tareas del Proyecto
            </Typography>
            <List>
                {/* Aquí agregarías las tareas, utilizando mock data o la data real */}
                {/* exampleTasks.map((task) => (
                    <ListItem key={task.id}>
                        <ListItemText
                            primary={task.name}
                            secondary={task.description}
                        />
                    </ListItem>
                )) */}
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
                        <InputLabel id="category-select-label">
                            Categoría
                        </InputLabel>
                        <Select
                            labelId="category-select-label"
                            value={selectedCategory}
                            onChange={(e) =>
                                setSelectedCategory(
                                    Number(e.target.value) || ""
                                )
                            }
                            label="Categoría"
                        >
                            {listCategories.map((category) => (
                                <MenuItem key={category.id} value={category.id}>
                                    {category.name}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>

                    <FormControl fullWidth margin="dense">
                        <InputLabel id="user-select-label">
                            Responsable
                        </InputLabel>
                        <Select
                            labelId="user-select-label"
                            value={selectedUser}
                            onChange={(e) =>
                                setSelectedUser(Number(e.target.value) || "")
                            }
                            label="Responsable"
                        >
                            {listUsers.map((user) => (
                                <MenuItem key={user.id} value={user.id}>
                                    {user.email}{" "}
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

            <Dialog
                open={openCategoryDialog}
                onClose={handleCloseCategoryDialog}
            >
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
