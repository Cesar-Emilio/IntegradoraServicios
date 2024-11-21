package mx.edu.utex.todolist.proyect.model;

import jakarta.validation.constraints.NotNull;

public class ProyectDTO {
    @NotNull(groups = {Register.class, Modify.class},message = "El nombre es requerido")
    private String name;

    @NotNull(groups = {Register.class, Modify.class},message = "La abreviación es requerida")
    private String abreviation;

    @NotNull(groups = {Register.class, Modify.class},message = "La descripción es requerida")
    private String description;

    @NotNull(groups = {ChangeStatus.class},message = "El estado es requerido")
    private boolean status;

    public ProyectDTO() {
    }

    public ProyectDTO(String name, String abreviation, String  description, boolean status) {
        this.name = name;
        this.abreviation = abreviation;
        this.description = description;
        this.status = status;
    }

    public ProyectDTO(String name, String abreviation,String description) {
        this.name = name;
        this.abreviation = abreviation;
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

    public String getAbreviation() {
        return abreviation;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public interface Register {
    }

    public interface Modify {
    }

    public interface ChangeStatus {
    }
}
