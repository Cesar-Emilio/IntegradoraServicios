package mx.edu.utex.todolist.category.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(Long id);  // findById debería estar aquí automáticamente con JpaRepository

    List<Category> findByStatusIsTrue();
    List<Category> findByStatusIsFalse();
    Optional<Category> findByName(String name);
}
