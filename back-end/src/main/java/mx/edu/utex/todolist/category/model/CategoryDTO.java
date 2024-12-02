package mx.edu.utex.todolist.category.model;

import jakarta.validation.constraints.NotNull;
import mx.edu.utex.todolist.proyect.model.ProyectDTO;

public class CategoryDTO {
    @NotNull(groups = {Register.class, Modify.class},message = "El nombre es requerido")
    private String name;

    @NotNull(groups = {Register.class, Modify.class},message = "La descripción es requerida")
    private String description;

    @NotNull(groups = {Modify.class},message = "El id del proyecto es requerido")
    private Long proyect_id;

    @NotNull(groups = {Modify.class},message = "El id de la tarea es requerido")
    private Long task_id;

    public CategoryDTO() {

    }

    public CategoryDTO(String name, String  description) {
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

    public Long getProyect_id() {
        return proyect_id;
    }

    public void setProyect_id(Long proyect_id) {
        this.proyect_id = proyect_id;
    }

    public Long getTask_id() {
        return task_id;
    }

    public void setTask_id(Long task_id) {
        this.task_id = task_id;
    }

    public interface Register {
    }

    public interface Modify {
    }

    public interface ChangeStatus {
    }
}
