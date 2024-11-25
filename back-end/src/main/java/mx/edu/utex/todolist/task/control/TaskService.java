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
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Registrar tarea
    public ResponseEntity<Message> registerTask(Task task) {
        if (task.getProyect() == null || task.getCategory() == null) {
            return new ResponseEntity<>(new Message("El proyecto y la categoría son obligatorios", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        Optional<Task> existingTask = taskRepository.findByName(task.getName());
        if (existingTask.isPresent()) {
            return new ResponseEntity<>(new Message("Ya existe una tarea con el mismo nombre", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        taskRepository.save(task);
        return new ResponseEntity<>(new Message("Se registró la tarea con éxito", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    // Consultar tareas
    public ResponseEntity<Message> consultTask() {
        List<Task> tasks = taskRepository.findAll();
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(new Message("No hay tareas", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new Message(tasks, "Listado de tareas", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Actualizar tarea
    public ResponseEntity<Message> updateTask(Task task, Long id) {
        Optional<Task> existingTask = taskRepository.findById(id);
        if (!existingTask.isPresent()) {
            return new ResponseEntity<>(new Message("Tarea no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        Task updatedTask = existingTask.get();
        updatedTask.setName(task.getName());
        updatedTask.setDescription(task.getDescription());
        updatedTask.setCategory(task.getCategory());
        updatedTask.setProyect(task.getProyect());

        taskRepository.save(updatedTask);
        return new ResponseEntity<>(new Message("Tarea actualizada correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Activar tarea
    public ResponseEntity<Message> activateTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()) {
            return new ResponseEntity<>(new Message("Tarea no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        Task updatedTask = task.get();
        if (updatedTask.isStatus()) {
            return new ResponseEntity<>(new Message("La tarea ya está habilitada", TypesResponse.WARNING), HttpStatus.OK);
        }

        updatedTask.setStatus(true);
        taskRepository.save(updatedTask);
        return new ResponseEntity<>(new Message("Tarea habilitada correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Desactivar tarea
    public ResponseEntity<Message> desactivateTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()) {
            return new ResponseEntity<>(new Message("Tarea no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        Task updatedTask = task.get();
        if (!updatedTask.isStatus()) {
            return new ResponseEntity<>(new Message("La tarea ya está deshabilitada", TypesResponse.WARNING), HttpStatus.OK);
        }

        updatedTask.setStatus(false);
        taskRepository.save(updatedTask);
        return new ResponseEntity<>(new Message("Tarea deshabilitada correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }
}