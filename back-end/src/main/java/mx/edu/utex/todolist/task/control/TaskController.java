package mx.edu.utex.todolist.task.control;

import mx.edu.utex.todolist.category.control.CategoryService;
import mx.edu.utex.todolist.task.model.Task;
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
    public ResponseEntity<Message> registerTask(@RequestBody Task task) {
        return taskService.registerTask(task);
    }

    @GetMapping("/consult")
    public ResponseEntity<Message> consultTasks() {
        return taskService.consultTask();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Message> updateTask(@RequestBody Task task, @PathVariable Long id) {
        return taskService.updateTask(task, id);
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<Message> activateTask(@PathVariable Long id) {
        return taskService.activateTask(id);
    }

    @PutMapping("/desactivate/{id}")
    public ResponseEntity<Message> desactivateTask(@PathVariable Long id) {
        return taskService.desactivateTask(id);
    }
}