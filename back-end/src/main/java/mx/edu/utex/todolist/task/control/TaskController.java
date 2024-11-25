package mx.edu.utex.todolist.task.control;

import mx.edu.utex.todolist.category.control.CategoryService;
import mx.edu.utex.todolist.task.model.Task;
import mx.edu.utex.todolist.task.model.TaskDTO;
import mx.edu.utex.todolist.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/register")
    public ResponseEntity<Message> registerTask(@RequestBody TaskDTO task) {
        return taskService.register(task);
    }

    @GetMapping("/findAll")
    public ResponseEntity<Message> findAll() {
        return taskService.findAll();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Message> updateTask(@RequestBody TaskDTO dto, @PathVariable Long id) {
        return taskService.update(dto, id);
    }

    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<Message> changeStatus(@PathVariable Long id) {
        return taskService.changeStatus(id);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Message> findById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteTask(@PathVariable Long id) {
        return taskService.delete(id);
    }
}