package mx.edu.utex.todolist.task.model;

import mx.edu.utex.todolist.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findById(Long id);  // findById debería estar aquí automáticamente con JpaRepository

    List<Task> findByStatusTrue();
    List<Task> findByStatusFalse();
    Optional<Task> findByName(String name);
}
