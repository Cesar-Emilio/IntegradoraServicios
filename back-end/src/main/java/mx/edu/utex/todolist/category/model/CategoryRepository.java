package mx.edu.utex.todolist.category.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByStatusIsTrue();
    List<Category> findByStatusIsFalse() ;
    Optional<Category> findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.id = ?1 AND c.proyect.id = ?2")
    Optional<Category> findCategoryByIdAndProyectId(Long id, Long proyect_id);

    @Query("SELECT c FROM Category c WHERE c.proyect.id = ?1")
    List<Category> findByProyectId(Long proyect_id);
}
