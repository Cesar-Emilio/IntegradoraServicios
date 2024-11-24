package mx.edu.utex.todolist.task.control;

import mx.edu.utex.todolist.category.control.CategoryService;
import mx.edu.utex.todolist.task.model.Task;
import mx.edu.utex.todolist.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Registrar categoría
    @PostMapping("/register")
    public ResponseEntity<Message> registerTask(@RequestBody Task task) {
        return taskService.registerTask(task);
    }

    // Consultar todas las categorías
    @GetMapping("/findAll")
    public ResponseEntity<Message> findAll() {
        return taskService.consultTask();
    }

    // Consultar categoría por ID
    @GetMapping("/find/{id}")
    public ResponseEntity<Message> findById(@PathVariable Long id) {
        return taskService.idTask(id);
    }

    // Eliminar categoría
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }

    // Actualizar categoría
    @PutMapping("/update/{id}")
    public ResponseEntity<Message> updateTask(@RequestBody Task task, @PathVariable Long id) {
        return taskService.updateTask(task, id);
    }

    // Activar categoría
    @PutMapping("/activate/{id}")
    public ResponseEntity<Message> activateTask(@PathVariable Long id) {
        return taskService.activateTask(id);
    }

    // Desactivar categoría
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Message> desactivateTask(@PathVariable Long id) {
        return taskService.desactivateTask(id);
    }

    // Añadir nueva categoría
    @PostMapping("/add")
    public ResponseEntity<Message> addCategory(@RequestBody Task task) {
        return taskService.addTask(task);
    }
}
