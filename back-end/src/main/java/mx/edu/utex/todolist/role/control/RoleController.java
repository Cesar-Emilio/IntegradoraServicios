package mx.edu.utex.todolist.role.control;


import mx.edu.utex.todolist.category.control.CategoryService;
import mx.edu.utex.todolist.role.model.Role;
import mx.edu.utex.todolist.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Registrar categoría
    @PostMapping("/register")
    public ResponseEntity<Message> registerRol(@RequestBody Role role) {
        return roleService.registerRol(role);
    }

    // Consultar todas las categorías
    @GetMapping("/all")
    public ResponseEntity<Message> findAll() {
        return roleService.consultRoles();
    }

    // Consultar categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable Long id) {
        return roleService.idRol(id);
    }

    // Eliminar categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteRol(@PathVariable Long id) {
        return roleService.deleteRol(id);
    }

    // Actualizar categoría
    @PutMapping("/update")
    public ResponseEntity<Message> updateRol(@RequestBody Role role) {
        return roleService.updateRol(role);
    }

    // Añadir nueva categoría
    @PostMapping("/add")
    public ResponseEntity<Message> addRol(@RequestBody Role role) {
        return roleService.addRol(role);
    }
}
