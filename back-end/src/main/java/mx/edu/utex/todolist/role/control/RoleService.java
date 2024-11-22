package mx.edu.utex.todolist.role.control;

import jakarta.transaction.Transactional;
import mx.edu.utex.todolist.category.model.Category;
import mx.edu.utex.todolist.category.model.CategoryRepository;
import mx.edu.utex.todolist.role.model.Role;
import mx.edu.utex.todolist.role.model.RoleRepository;
import mx.edu.utex.todolist.utils.Message;
import mx.edu.utex.todolist.utils.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Registrar Role
    public ResponseEntity<Message> registerRol(Role role) {
        Optional<Role> existingRole = roleRepository.findByName(role.getName());
        if (existingRole.isPresent()) {
            return new ResponseEntity<>(new Message("Ya existe un role con el mismo nombre", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        roleRepository.save(role);  // Guardamos la categoría
        return new ResponseEntity<>(new Message("Se registró el nuevo role", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    // Consultar todos los Roles
    public ResponseEntity<Message> consultRoles() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            return new ResponseEntity<>(new Message("No hay roles", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(roles, "Listado de roles", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Consultar Roles por ID
    public ResponseEntity<Message> idRol(Long id) {
        Optional<Role> roles = roleRepository.findById(id);
        if (!roles.isPresent()) {
            return new ResponseEntity<>(new Message("Categoría no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(roles.get(), "Categoría encontrada", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Eliminar Rol
    public ResponseEntity<Message> deleteRol(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (!role.isPresent()) {
            return new ResponseEntity<>(new Message("Role no encontrado", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        roleRepository.deleteById(id);
        return new ResponseEntity<>(new Message("Role eliminado", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Actualizar Rol
    public ResponseEntity<Message> updateRol(Role role) {
        Optional<Role> existingRole = roleRepository.findById(role.getId());
        if (!existingRole.isPresent()) {
            return new ResponseEntity<>(new Message("Categoría no encontrada", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        roleRepository.save(role);
        return new ResponseEntity<>(new Message("Categoría actualizada", TypesResponse.SUCCESS), HttpStatus.OK);
    }


    // Añadir Rol
    public ResponseEntity<Message> addRol(Role role) {
        Optional<Role> existingRole = roleRepository.findByName(role.getName());
        if (existingRole.isPresent()) {
            return new ResponseEntity<>(new Message("Rol ya existente", TypesResponse.ERROR), HttpStatus.CONFLICT);
        }

        roleRepository.save(role);
        return new ResponseEntity<>(new Message("Rol agregado con éxito", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }
}
