package mx.edu.utex.todolist.task.model;

import jakarta.validation.constraints.NotNull;
import mx.edu.utex.todolist.proyect.model.ProyectDTO;

import java.util.List;

public class TaskDTO {
    @NotNull(groups = {ProyectDTO.Register.class, ProyectDTO.Modify.class}, message = "El nombre es requerido")
    private String name;

    @NotNull(groups = {ProyectDTO.Register.class, ProyectDTO.Modify.class}, message = "La descripción es requerida")
    private String description;

    @NotNull(groups = {ProyectDTO.Register.class, ProyectDTO.Modify.class}, message = "El id de la categoría es requerido")
    private Long category_id;

    @NotNull(groups = {ProyectDTO.Register.class, ProyectDTO.Modify.class}, message =
            "El id del proyecto es requerido")
    private Long proyect_id;

    @NotNull(groups = {ProyectDTO.Register.class, ProyectDTO.Modify.class}, message =
            "El id del usuario es requerido")
    private List<Long> responsibles_id;


    public TaskDTO() {}

    public TaskDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public TaskDTO(String name, String description, long category_id, long proyect_id, List<Long> responsibles_id) {
        this.name = name;
        this.description = description;
        this.category_id = category_id;
        this.proyect_id = proyect_id;
        this.responsibles_id = responsibles_id;
    }

    // Getters y Setters
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

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public Long getProyect_id() {
        return proyect_id;
    }

    public void setProyect_id(Long proyect_id) {
        this.proyect_id = proyect_id;
    }

    public List<Long> getResponsibles_id() {
        return responsibles_id;
    }

    public void setResponsibles_id(List<Long> responsibles_id) {
        this.responsibles_id = responsibles_id;
    }

    public interface Register {
    }

    public interface Modify {
    }

    public interface ChangeStatus {
    }
}