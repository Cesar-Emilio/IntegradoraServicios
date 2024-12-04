package mx.edu.utex.todolist.proyect.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProyectRepository extends JpaRepository<Proyect, Long> {
    Optional<Proyect> findByName(String name);

    List<Proyect> findByStatusIsTrue();

    List<Proyect> findByStatusIsFalse();

    @Query("SELECT p FROM Proyect p JOIN p.tasks t WHERE p.id = ?1 AND t.id = ?2")
    Optional<Proyect> findProyectByIdAndTaskId(Long proyectId, Long taskId);
}
