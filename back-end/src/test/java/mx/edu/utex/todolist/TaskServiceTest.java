package mx.edu.utex.todolist;

import mx.edu.utex.todolist.category.control.CategoryService;
import mx.edu.utex.todolist.category.model.CategoryDTO;
import mx.edu.utex.todolist.category.model.CategoryRepository;
import mx.edu.utex.todolist.proyect.control.ProyectService;
import mx.edu.utex.todolist.proyect.model.ProyectDTO;
import mx.edu.utex.todolist.proyect.model.ProyectRepository;
import mx.edu.utex.todolist.task.control.TaskService;
import mx.edu.utex.todolist.task.model.TaskDTO;
import mx.edu.utex.todolist.task.model.TaskRepository;
import mx.edu.utex.todolist.user.control.UserService;
import mx.edu.utex.todolist.user.model.UserDTO;
import mx.edu.utex.todolist.user.model.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TaskServiceTest{
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProyectService proyectService;
    @Autowired
    private ProyectRepository proyectRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void setUpData() {
        taskRepository.deleteAll();
        categoryRepository.deleteAll();
        proyectRepository.deleteAll();
        userRepository.deleteAll();

        UserDTO userDTO = new UserDTO("Erick", "Teja", "erickhumbetotc@gmail.com", 777480654, "12345", "ROLE_USER");
        userService.register(userDTO);
        List<Long> list = new ArrayList<>();
        list.add(userRepository.findByEmail("erickhumbetotc@gmail.com").get().getId());

        ProyectDTO proyectDTO = new ProyectDTO("Controlador Tareas", "CT", "Una aplicación para controlar tus tareas", list);
        proyectService.register(proyectDTO);

        CategoryDTO categoryDTO = new CategoryDTO("Pendiente", "Esta es una categoria de tareas pendientes", proyectRepository.findByName("Controlador Tareas").get().getId(), true);
        categoryService.registerCategory(categoryDTO);

    }

    @AfterEach
    void setUpData2() {
        taskRepository.deleteAll();
        categoryRepository.deleteAll();
        proyectRepository.deleteAll();
        userRepository.deleteAll();

    }

    @Test
    public void testRegister() {
        Long userId = userRepository.findByEmail("erickhumbetotc@gmail.com")
                .orElseThrow(() -> new AssertionError("Usuario no encontrado"))
                .getId();

        Long proyectId = proyectRepository.findByName("Controlador Tareas")
                .orElseThrow(() -> new AssertionError("Proyecto no encontrado"))
                .getId();

        Long categoryId = categoryRepository.findByName("Pendiente")
                .orElseThrow(() -> new AssertionError("Categoría no encontrada"))
                .getId();

        List<Long> list = new ArrayList<>();
        list.add(userId);
        TaskDTO taskDTO = new TaskDTO("Integradora Servicios", "Es el proyecto final de la materia de Servicios", categoryId, proyectId, list);

        Assertions.assertEquals(HttpStatus.CREATED, taskService.register(taskDTO).getStatusCode());
    }
    @Test
    public void testRegisterFail() {
        Long userId = userRepository.findByEmail("erickhumbetotc@gmail.com")
                .orElseThrow(() -> new AssertionError("Usuario no encontrado"))
                .getId();

        Long proyectId = proyectRepository.findByName("Controlador Tareas")
                .orElseThrow(() -> new AssertionError("Proyecto no encontrado"))
                .getId();

        Long categoryId = categoryRepository.findByName("Pendiente")
                .orElseThrow(() -> new AssertionError("Categoría no encontrada"))
                .getId();

        List<Long> list = new ArrayList<>();
        TaskDTO taskDTO = new TaskDTO("Integradora Servicios", "Es el proyecto final de la materia de Servicios", categoryId, proyectId, list);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, taskService.register(taskDTO).getStatusCode());
    }


    @Test
    public void testFindAllTasks() {
        Long proyectId = proyectRepository.findByName("Controlador Tareas")
                .orElseThrow(() -> new AssertionError("Proyecto no encontrado"))
                .getId();

        Assertions.assertEquals(HttpStatus.OK, taskService.findAll(proyectId).getStatusCode());
    }

    @Test
    public void testUpdateTask() {
        Long userId = userRepository.findByEmail("erickhumbetotc@gmail.com")
                .orElseThrow(() -> new AssertionError("Usuario no encontrado"))
                .getId();

        Long proyectId = proyectRepository.findByName("Controlador Tareas")
                .orElseThrow(() -> new AssertionError("Proyecto no encontrado"))
                .getId();

        Long categoryId = categoryRepository.findByName("Pendiente")
                .orElseThrow(() -> new AssertionError("Categoría no encontrada"))
                .getId();

        List<Long> list = new ArrayList<>();
        list.add(userId);
        taskService.register(new TaskDTO("Integradora Servicios", "Proyecto final", categoryId, proyectId, list));

        Long taskId = taskRepository.findByName("Integradora Servicios")
                .orElseThrow(() -> new AssertionError("Tarea no encontrada"))
                .getId();

        TaskDTO updatedTask = new TaskDTO("Integradora Modificada", "Descripción modificada", categoryId, proyectId, list);
        Assertions.assertEquals(HttpStatus.CREATED, taskService.update(updatedTask, taskId).getStatusCode());
    }

    @Test
    public void testUpdateTaskFail() {
        Long userId = userRepository.findByEmail("erickhumbetotc@gmail.com")
                .orElseThrow(() -> new AssertionError("Usuario no encontrado"))
                .getId();

        Long proyectId = proyectRepository.findByName("Controlador Tareas")
                .orElseThrow(() -> new AssertionError("Proyecto no encontrado"))
                .getId();

        Long categoryId = categoryRepository.findByName("Pendiente")
                .orElseThrow(() -> new AssertionError("Categoría no encontrada"))
                .getId();

        List<Long> list = new ArrayList<>();
        list.add(userId);
        taskService.register(new TaskDTO("Integradora Servicios", "Proyecto final", categoryId, proyectId, list));

        Long taskId = taskRepository.findByName("Integradora Servicios")
                .orElseThrow(() -> new AssertionError("Tarea no encontrada"))
                .getId();

        TaskDTO updatedTask = new TaskDTO("Integradora Modificada", "Descripción modificada", categoryId, 9999L, list);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, taskService.update(updatedTask, taskId).getStatusCode());
    }

    @Test
    public void testChangeStatus() {
        Long userId = userRepository.findByEmail("erickhumbetotc@gmail.com")
                .orElseThrow(() -> new AssertionError("Usuario no encontrado"))
                .getId();

        Long proyectId = proyectRepository.findByName("Controlador Tareas")
                .orElseThrow(() -> new AssertionError("Proyecto no encontrado"))
                .getId();

        Long categoryId = categoryRepository.findByName("Pendiente")
                .orElseThrow(() -> new AssertionError("Categoría no encontrada"))
                .getId();

        List<Long> list = new ArrayList<>();
        list.add(userId);
        TaskDTO taskDTO = new TaskDTO("Integradora Servicios", "Proyecto final de la materia", categoryId, proyectId, list);

        taskService.register(taskDTO);

        Long taskId = taskRepository.findByName("Integradora Servicios")
                .orElseThrow(() -> new AssertionError("Tarea no encontrada"))
                .getId();

        Assertions.assertEquals(HttpStatus.OK, taskService.changeStatus(taskId).getStatusCode());
    }

    @Test
    public void testChangeStatusFail() {
        Long userId = userRepository.findByEmail("erickhumbetotc@gmail.com")
                .orElseThrow(() -> new AssertionError("Usuario no encontrado"))
                .getId();

        Long proyectId = proyectRepository.findByName("Controlador Tareas")
                .orElseThrow(() -> new AssertionError("Proyecto no encontrado"))
                .getId();

        Long categoryId = categoryRepository.findByName("Pendiente")
                .orElseThrow(() -> new AssertionError("Categoría no encontrada"))
                .getId();

        List<Long> list = new ArrayList<>();
        list.add(userId);
        TaskDTO taskDTO = new TaskDTO("Integradora Servicios", "Proyecto final de la materia", categoryId, proyectId, list);
        taskService.register(taskDTO);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, taskService.changeStatus(9999L).getStatusCode());
    }

    @Test
    public void testDeleteTask() {
        Long userId = userRepository.findByEmail("erickhumbetotc@gmail.com")
                .orElseThrow(() -> new AssertionError("Usuario no encontrado"))
                .getId();

        Long proyectId = proyectRepository.findByName("Controlador Tareas")
                .orElseThrow(() -> new AssertionError("Proyecto no encontrado"))
                .getId();

        Long categoryId = categoryRepository.findByName("Pendiente")
                .orElseThrow(() -> new AssertionError("Categoría no encontrada"))
                .getId();

        List<Long> list = new ArrayList<>();
        list.add(userId);
        TaskDTO taskDTO = new TaskDTO("Integradora Servicios", "Es el proyecto final de la materia de Servicios", categoryId, proyectId, list);

        taskService.register(taskDTO);

        Long taskId = taskRepository.findByName("Integradora Servicios")
                .orElseThrow(() -> new AssertionError("Tarea no encontrada"))
                .getId();

        Assertions.assertEquals(HttpStatus.OK, taskService.delete(taskId).getStatusCode());
    }

    @Test
    public void testDeleteTaskFail() {
        Long userId = userRepository.findByEmail("erickhumbetotc@gmail.com")
                .orElseThrow(() -> new AssertionError("Usuario no encontrado"))
                .getId();

        Long proyectId = proyectRepository.findByName("Controlador Tareas")
                .orElseThrow(() -> new AssertionError("Proyecto no encontrado"))
                .getId();

        Long categoryId = categoryRepository.findByName("Pendiente")
                .orElseThrow(() -> new AssertionError("Categoría no encontrada"))
                .getId();

        List<Long> list = new ArrayList<>();
        list.add(userId);
        TaskDTO taskDTO = new TaskDTO("Integradora Servicios", "Es el proyecto final de la materia de Servicios", categoryId, proyectId, list);

        taskService.register(taskDTO);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, taskService.delete(9999L).getStatusCode());
    }

    @Test
    public void testFindById() {
        Long userId = userRepository.findByEmail("erickhumbetotc@gmail.com")
                .orElseThrow(() -> new AssertionError("Usuario no encontrado"))
                .getId();

        Long proyectId = proyectRepository.findByName("Controlador Tareas")
                .orElseThrow(() -> new AssertionError("Proyecto no encontrado"))
                .getId();

        Long categoryId = categoryRepository.findByName("Pendiente")
                .orElseThrow(() -> new AssertionError("Categoría no encontrada"))
                .getId();

        List<Long> list = new ArrayList<>();
        list.add(userId);
        TaskDTO taskDTO = new TaskDTO("Integradora Servicios", "Proyecto final de la materia", categoryId, proyectId, list);

        taskService.register(taskDTO);

        Long taskId = taskRepository.findByName("Integradora Servicios")
                .orElseThrow(() -> new AssertionError("Tarea no encontrada"))
                .getId();

        Assertions.assertEquals(HttpStatus.OK, taskService.findById(taskId).getStatusCode());
    }

    @Test
    public void testFindByIdFail() {
        Long userId = userRepository.findByEmail("erickhumbetotc@gmail.com")
                .orElseThrow(() -> new AssertionError("Usuario no encontrado"))
                .getId();

        Long proyectId = proyectRepository.findByName("Controlador Tareas")
                .orElseThrow(() -> new AssertionError("Proyecto no encontrado"))
                .getId();

        Long categoryId = categoryRepository.findByName("Pendiente")
                .orElseThrow(() -> new AssertionError("Categoría no encontrada"))
                .getId();

        List<Long> list = new ArrayList<>();
        list.add(userId);
        TaskDTO taskDTO = new TaskDTO("Integradora Servicios", "Proyecto final de la materia", categoryId, proyectId, list);

        taskService.register(taskDTO);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, taskService.findById(9999L).getStatusCode());
    }

}