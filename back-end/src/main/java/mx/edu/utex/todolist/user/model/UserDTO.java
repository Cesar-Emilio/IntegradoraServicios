package mx.edu.utex.todolist.user.model;

import jakarta.validation.constraints.NotNull;

public class UserDTO {
    @NotNull(groups = {Register.class, Modify.class}, message = "El nombre no puede ser nulo")
    private String name;

    @NotNull(groups = {Register.class, Modify.class}, message = "El apellido no puede ser nulo")
    private String lastname;

    @NotNull(groups = {Register.class, Modify.class}, message = "El email no puede ser nulo")
    private String email;

    @NotNull(groups = {Register.class, Modify.class}, message = "El telefono no puede ser nulo")
    private int phone;

    @NotNull(groups = {Register.class, Modify.class}, message = "El password no puede ser nulo")
    private String password;

    @NotNull(groups = {Register.class, Modify.class}, message = "El admin no puede ser nulo")
    private String admin;

    @NotNull(groups = {Modify.class}, message = "Se debe agregar el id del proyecto")
    private long proyect_id;

    @NotNull(groups = {Modify.class}, message = "Se debe agregar el id de la tarea")
    private long task_id;


    public UserDTO() {
    }

    public UserDTO(String name, String lastname, String email, int phone, String password, String admin) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.admin = admin;
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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
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

    public interface Register {}
    public interface Modify {}
    public interface ChangeStatus {}
}
