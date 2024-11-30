package mx.edu.utex.todolist.user.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);

    @Query("SELECT u FROM User u JOIN u.proyects p WHERE u.id = ?1 AND p.id = ?2")
    Optional<User> findUserByIdAndProyectId(Long userId, Long proyectId);

    @Query("SELECT u FROM User u JOIN u.tasks t WHERE u.id IN :userIds AND t.proyect.id = :proyectId")
    List<User> findUsersByIdsAndProyectId(@Param("userIds") List<Long> userIds, @Param("proyectId") Long proyectId);
}
