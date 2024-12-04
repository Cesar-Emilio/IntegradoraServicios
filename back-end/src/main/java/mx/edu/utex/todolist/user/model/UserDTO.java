package mx.edu.utex.todolist.user.model;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class UserDTO {
    @NotNull(groups = {Register.class, Modify.class}, message = "El nombre no puede ser nulo")
    private String name;

    @NotNull(groups = {Register.class, Modify.class}, message = "El apellido no puede ser nulo")
    private String lastname;

    @NotNull(groups = {Register.class, Modify.class}, message = "El email no puede ser nulo")
    private String email;

    @NotNull(groups = {Register.class, Modify.class}, message = "El telefono no puede ser nulo")
    private long phone;

    @NotNull(groups = {Register.class, Modify.class}, message = "El password no puede ser nulo")
    private String password;

    @NotNull(groups = {Register.class, Modify.class}, message = "El admin no puede ser nulo")
    private String admin;

    @NotNull(groups = {Modify.class}, message = "Se debe agregar el id del proyecto")
    private List<Long> proyect_id;

    @NotNull(groups = {Modify.class}, message = "Se debe agregar el id de la tarea")
    private List<Long> task_id;

    public UserDTO() {
    }

    public UserDTO(String name, String lastname, String email, long phone, String password, String admin) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.admin = admin;
    }
    public UserDTO(String name, String lastname, String email, long phone, String password, String admin, List<Long> proyect_id) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.admin = admin;
        this.proyect_id = proyect_id;
    }
    public UserDTO(String name, String lastname, String email, long phone, String password, String admin, List<Long> proyect_id, List<Long> task_id) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.admin = admin;
        this.proyect_id = proyect_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public List<Long> getTask_id() {
        return task_id;
    }

    public void setTask_id(List<Long> task_id) {
        this.task_id = task_id;
    }

    public List<Long> getProyect_id() {
        return proyect_id;
    }

    public void setProyect_id(List<Long> proyect_id) {
        this.proyect_id = proyect_id;
    }

    public interface Register {}
    public interface Modify {}
    public interface ChangeStatus {}
}