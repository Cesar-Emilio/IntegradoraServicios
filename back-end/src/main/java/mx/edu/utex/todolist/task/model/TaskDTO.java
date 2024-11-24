package mx.edu.utex.todolist.task.model;

import jakarta.validation.constraints.NotNull;
import mx.edu.utex.todolist.proyect.model.ProyectDTO;

public class TaskDTO {
    @NotNull(groups = {ProyectDTO.Register.class, ProyectDTO.Modify.class},message = "El nombre es requerido")
    private String name;

    @NotNull(groups = {ProyectDTO.Register.class, ProyectDTO.Modify.class},message = "La descripción es requerida")
    private String description;

    @NotNull(groups = {ProyectDTO.ChangeStatus.class},message = "El estado es requerido")
    private boolean status;

    public TaskDTO() {

    }

    public TaskDTO(String name, String  description, boolean status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public TaskDTO(String name,String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public interface Register {
    }

    public interface Modify {
    }

    public interface ChangeStatus {
    }
}
