package mx.edu.utex.todolist.task.control;

import jakarta.transaction.Transactional;
import mx.edu.utex.todolist.task.model.Task;
import mx.edu.utex.todolist.task.model.TaskRepository;
import mx.edu.utex.todolist.utils.Message;
import mx.edu.utex.todolist.utils.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Registrar Tarea
    public ResponseEntity<Message> registerTask(Task task) {
        Optional<Task> existingTask = taskRepository.findByName(task.getName());
        if (existingTask.isPresent()) {
            return new ResponseEntity<>(new Message("Ya existe una tarea con el mismo nombre", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        taskRepository.save(task);  // Guardamos la categoría
        return new ResponseEntity<>(new Message("Se registró la tarea", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    // Consultar todas las Tareas
    public ResponseEntity<Message> consultTask() {
        List<Task> tasks = taskRepository.findAll();
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(new Message("No hay tareas", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(tasks, "Listado de tareas", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Consultar Tarea por ID
    public ResponseEntity<Message> idTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()) {
            return new ResponseEntity<>(new Message("Tarea no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(task.get(), "Task encontrada", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Eliminar Tarea
    public ResponseEntity<Message> deleteTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()) {
            return new ResponseEntity<>(new Message("Tarea no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        taskRepository.deleteById(id);
        return new ResponseEntity<>(new Message("Tarea eliminada", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Actualizar Tarea
    public ResponseEntity<Message> updateTask(Task task, Long id) {
        Optional<Task> existingTask = taskRepository.findById(task.getId());
        if (!existingTask.isPresent()) {
            return new ResponseEntity<>(new Message("Task no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        taskRepository.save(task);
        return new ResponseEntity<>(new Message("Tarea actualizada", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Activar Tarea
    public ResponseEntity<Message> activateTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()) {
            return new ResponseEntity<>(new Message("Tarea no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        Task updatedTask = task.get();
        if (updatedTask.isStatus()) {
            return new ResponseEntity<>(new Message("La Tarea ya está activa", TypesResponse.WARNING), HttpStatus.OK);
        }

        updatedTask.setStatus(true);
        taskRepository.save(updatedTask);

        return new ResponseEntity<>(new Message("Tarea activada correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Desactivar Tarea
    public ResponseEntity<Message> desactivateTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()) {
            return new ResponseEntity<>(new Message("Tarea no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        Task updatedTask = task.get();
        if (!updatedTask.isStatus()) {
            return new ResponseEntity<>(new Message("La Tarea ya está desactivada", TypesResponse.WARNING), HttpStatus.OK);
        }

        updatedTask.setStatus(false);
        taskRepository.save(updatedTask);
        return new ResponseEntity<>(new Message("Tarea desactivada correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Añadir Tarea
    public ResponseEntity<Message> addTask(Task task) {
        Optional<Task> existingTask = taskRepository.findByName(task.getName());
        if (existingTask.isPresent()) {
            return new ResponseEntity<>(new Message("Tarea ya existente", TypesResponse.ERROR), HttpStatus.CONFLICT);
        }

        taskRepository.save(task);
        return new ResponseEntity<>(new Message("Tarea agregada con éxito", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }
}
