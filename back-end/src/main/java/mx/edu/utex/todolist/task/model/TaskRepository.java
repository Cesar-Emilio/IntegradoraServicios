package mx.edu.utex.todolist.task.model;

import mx.edu.utex.todolist.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findById(Long id);

    List<Task> findByStatusTrue();
    List<Task> findByStatusFalse();
    Optional<Task> findByName(String name);

    @Query("SELECT t FROM Task t WHERE t.proyect.id = ?1")
    List<Task> findByProyectId(Long proyect_id);

    @Query("SELECT t FROM Task t WHERE t.id = ?1 AND t.proyect.id = ?2")
    Optional<Task> findByIdAAndProyectId(Long id, Long proyect_id);

    @Query("SELECT t FROM Task t JOIN t.responsibles u WHERE t.id = ?1 AND t.proyect.id = ?2 AND u.id = ?3")
    Optional<Task> findByIdAndProyectIdAndResponsible(Long taskId, Long proyectId, Long userId);
}
