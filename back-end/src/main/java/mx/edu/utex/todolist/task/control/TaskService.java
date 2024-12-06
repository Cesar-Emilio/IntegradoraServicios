package mx.edu.utex.todolist.task.control;

import mx.edu.utex.todolist.category.model.Category;
import mx.edu.utex.todolist.category.model.CategoryRepository;
import mx.edu.utex.todolist.proyect.model.Proyect;
import mx.edu.utex.todolist.proyect.model.ProyectRepository;
import mx.edu.utex.todolist.task.model.Task;
import mx.edu.utex.todolist.task.model.TaskDTO;
import mx.edu.utex.todolist.task.model.TaskRepository;
import mx.edu.utex.todolist.user.model.User;
import mx.edu.utex.todolist.user.model.UserRepository;
import mx.edu.utex.todolist.utils.Message;
import mx.edu.utex.todolist.utils.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
@Service
public class TaskService {

    private final Logger logger = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final ProyectRepository proyectRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, ProyectRepository proyectRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.proyectRepository = proyectRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional(rollbackFor = SQLException.class)
    // Registrar tarea
    public ResponseEntity<Message> register(TaskDTO dto) {
        if(validateTaskDTOAttributes(dto)) {
            logger.error("Los atributos de la tarea exceden el límite de caracteres");
            return new ResponseEntity<>(new Message("Los atributos de la tarea no cumplen con las restricciones", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        if(!validateIdCategory(dto.getCategory_id(), dto.getProyect_id())) {
            logger.error("Id de categoría inválido");
            return new ResponseEntity<>(new Message("Id de categoría inválido", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        if(!validateIdProyect(dto.getProyect_id())) {
            logger.error("Id de proyecto inválido");
            return new ResponseEntity<>(new Message("Id de proyecto inválido", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        if (dto.getResponsibles_id().isEmpty()) {
            logger.error("Id de usuario inválido");
            return new ResponseEntity<>(new Message("Id de usuario inválido", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        if(!validateIdUser(dto.getResponsibles_id().get(0), dto.getProyect_id())){
            logger.error("Id user: " + dto.getResponsibles_id().get(0));
            logger.error("Id proyect: " + dto.getProyect_id());
            logger.error("Id de usuario inválido: " + dto.getResponsibles_id().get(0));
            return new ResponseEntity<>(new Message("Id de usuario inválido", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }


        Task task = new Task(dto.getName(), dto.getDescription(), true);

        Optional<Proyect> proyect = proyectRepository.findById(dto.getProyect_id());
        if(!proyect.isPresent()) {
            logger.error("Proyecto no encontrado");
            return new ResponseEntity<>(new Message("Proyecto no encontrado", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        task.setProyect(proyect.get());

        Optional<Category> category = categoryRepository.findById(dto.getCategory_id());
        if(!category.isPresent()) {
            logger.error("Categoría no encontrada");
            return new ResponseEntity<>(new Message("Categoría no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        task.setCategory(category.get());

        List<User> responsibles = userRepository.findUsersByIdsAndProyectId(dto.getResponsibles_id(), dto.getProyect_id());
        if(responsibles.isEmpty()) {
            logger.error("No se encontraron los usuarios");
            return new ResponseEntity<>(new Message("Usuario no encontrado", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        task.setResponsibles(responsibles);

        task = taskRepository.saveAndFlush(task);
        if(task == null) {
            logger.error("No se pudo registrar la tarea");
            return new ResponseEntity<>(new Message("No se pudo registrar la tarea", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<User> users = userRepository.findAllById(dto.getResponsibles_id());
        if (users.isEmpty()) {
            return new ResponseEntity<>(new Message("No se encontraron usuarios con los IDs proporcionados", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        for (User user : users) {
            user.getTasks().add(task);
        }

        logger.info("Tarea registrada correctamente");
        return new ResponseEntity<>(new Message("Tarea registrada correctamente", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    // Consultar tareas
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll(Long proyectId) {
        List<Task> tasks = taskRepository.findByProyectId(proyectId);
        logger.info("La búsqueda ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(tasks, "Listado de tareas", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<Message> update(TaskDTO dto, Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            logger.error("Tarea no encontrada");
            return new ResponseEntity<>(new Message("Tarea no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        if(validateTaskDTOAttributes(dto)) {
            logger.error("Los atributos de la tarea no cumplen con las restricciones");
            return new ResponseEntity<>(new Message("Los atributos de la tarea no cumplen con las restricciones", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        if(!validateIdCategory(dto.getCategory_id(), dto.getProyect_id())) {
            logger.error("Id de categoría inválido");
            return new ResponseEntity<>(new Message("Id de categoría inválido", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        if(!validateIdProyect(dto.getProyect_id())) {
            logger.error("Id de proyecto inválido");
            return new ResponseEntity<>(new Message("Id de proyecto inválido",
                    TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        if(dto.getResponsibles_id().stream().anyMatch(userId -> !validateIdUser(userId, dto.getProyect_id()))) {
            logger.error("Id de usuario inválido");
            return new ResponseEntity<>(new Message("Id de usuario inválido", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setStatus(true);

        if(dto.getProyect_id() == null){
            logger.error("No se puede modificar el proyecto de la tarea");
            return new ResponseEntity<>(new Message("No se puede modificar el proyecto de la tarea", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        Category category = categoryRepository.findCategoryByIdAndProyectId(dto.getCategory_id(), dto.getProyect_id()).orElse(null);
        if(category == null) {
            logger.error("Categoría no encontrada");
            return new ResponseEntity<>(new Message("Categoría no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        task.setCategory(category);

        List<User> responsibles = userRepository.findUsersByIdsAndProyectId(dto.getResponsibles_id(), dto.getProyect_id());
        if(responsibles.isEmpty()) {
            logger.error("Usuarios no encontrados");
            return new ResponseEntity<>(new Message("Usuario no encontrado", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        task.setResponsibles(responsibles);

        task = taskRepository.saveAndFlush(task);

        if(task == null) {
            logger.error("No se pudo registrar la tarea");
            return new ResponseEntity<>(new Message("No se pudo registrar la tarea", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("Tarea registrada correctamente");
        return new ResponseEntity<>(new Message("Tarea registrada correctamente", TypesResponse.SUCCESS), HttpStatus.CREATED);

    }

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<Message> changeStatus(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if(task == null) {
            logger.error("Tarea no encontrada");
            return new ResponseEntity<>(new Message("Tarea no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        task.setStatus(!task.isStatus());
        task = taskRepository.saveAndFlush(task);
        if(task == null) {
            logger.error("No se pudo actualizar la tarea");
            return new ResponseEntity<>(new Message("No se pudo actualizar la tarea", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Tarea actualizada correctamente");
        return new ResponseEntity<>(new Message("Tarea actualizada correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Eliminar tarea
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<Message> delete(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if(task == null) {
            logger.error("Tarea no encontrada");
            return new ResponseEntity<>(new Message("Tarea no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        taskRepository.deleteById(id);
        logger.info("Tarea eliminada correctamente");
        return new ResponseEntity<>(new Message("Tarea eliminada correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Consultar tarea por ID
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()) {
            logger.error("Tarea no encontrada");
            return new ResponseEntity<>(new Message("Tarea no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(task.get(), "Tarea encontrada", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    private boolean validateTaskDTOAttributes(TaskDTO dto) {
        return dto.getName() == null || dto.getName().length() > 50 || dto.getDescription() == null || dto.getDescription().length() > 50;
    }

    private boolean validateIdCategory(Long categoryId, Long proyectId) {
        return categoryRepository.findCategoryByIdAndProyectId(categoryId, proyectId).isPresent();
    }

    private boolean validateIdProyect(Long proyectId) {
        return proyectRepository.findById(proyectId).isPresent();
    }

    private boolean validateIdUser(Long userId, Long proyectId) {
        return userRepository.findUserByIdAndProyectId(userId, proyectId).isPresent();
    }
}