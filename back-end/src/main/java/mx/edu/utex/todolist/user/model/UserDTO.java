package mx.edu.utex.todolist.user.model;

import jakarta.validation.constraints.NotNull;

public class UserDTO {
    @NotNull(groups = {Register.class, Modify.class}, message = "El nombre no puede ser nulo")
    private String name;

    @NotNull(groups = {Register.class, Modify.class}, message = "El apellido no puede ser nulo")
    private String surname;

    @NotNull(groups = {Register.class, Modify.class}, message = "El email no puede ser nulo")
    private String email;

    @NotNull(groups = {Register.class, Modify.class}, message = "El telefono no puede ser nulo")
    private int cellphone;

    @NotNull(groups = {Register.class, Modify.class}, message = "El password no puede ser nulo")
    private String password;

    private boolean status;

    private boolean admin;

    public UserDTO(String name, String surname, String email, int cellphone, String password, boolean status, boolean admin) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.cellphone = cellphone;
        this.password = password;
        this.status = status;
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCellphone() {
        return cellphone;
    }

    public void setCellphone(int cellphone) {
        this.cellphone = cellphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public interface Register {}
    public interface Modify {}
    public interface ChangeStatus {}
}
