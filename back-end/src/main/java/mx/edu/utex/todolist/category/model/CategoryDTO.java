package mx.edu.utex.todolist.category.model;

import jakarta.validation.constraints.NotNull;
import mx.edu.utex.todolist.proyect.model.ProyectDTO;

public class CategoryDTO {
    @NotNull(groups = {Register.class, Modify.class},message = "El nombre es requerido")
    private String name;

    @NotNull(groups = {Register.class, Modify.class},message = "La descripción es requerida")
    private String description;

    @NotNull(groups = {Register.class, Modify.class},message = "El id del proyecto es requerido")
    private Long proyect_id;

    @NotNull(groups = {ProyectDTO.ChangeStatus.class},message = "El estado es requerido")
    private boolean status;

    public CategoryDTO(String name, String  description, Long proyect_id, boolean status) {
        this.name = name;
        this.description = description;
        this.proyect_id = proyect_id;
        this.status = status;
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

    public Long getProyect_id() {
        return proyect_id;
    }

    public void setProyect_id(Long proyect_id) {
        this.proyect_id = proyect_id;
    }

    @NotNull(groups = {ProyectDTO.ChangeStatus.class}, message = "El estado es requerido")
    public boolean isStatus() {
        return status;
    }

    public void setStatus(@NotNull(groups = {ProyectDTO.ChangeStatus.class}, message = "El estado es requerido") boolean status) {
        this.status = status;
    }

    public interface Register {
    }

    public interface Modify {
    }

    public interface ChangeStatus {
    }
}
