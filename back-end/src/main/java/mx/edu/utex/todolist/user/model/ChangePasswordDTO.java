package mx.edu.utex.todolist.user.model;

import jakarta.validation.constraints.NotNull;

public class ChangePasswordDTO {

    @NotNull(message = "La contraseña antigua no puede ser nula")
    private String oldPassword;

    @NotNull(message = "La nueva contraseña no puede ser nula")
    private String newPassword;

    public ChangePasswordDTO() {
    }

    public ChangePasswordDTO(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
