package mx.edu.utex.todolist.proyect.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProyectRepository extends JpaRepository<Proyect, Long> {
    Proyect findByName(String name);

    List<Proyect> findByStatusIsTrue();

    List<Proyect> findByStatusIsFalse();
}
