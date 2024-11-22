package mx.edu.utex.todolist.role.model;

import jakarta.validation.constraints.NotNull;
import mx.edu.utex.todolist.proyect.model.ProyectDTO;


public class RoleDTO {

    @NotNull(groups = {ProyectDTO.Register.class, ProyectDTO.Modify.class}, message = "El nombre es requerido")
    private String name;

    public RoleDTO() {
    }

    public RoleDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public interface Register {
    }

    public interface Modify {
    }
}
