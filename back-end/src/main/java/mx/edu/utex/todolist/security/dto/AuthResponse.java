package mx.edu.utex.todolist.security.dto;

public class AuthResponse {
    private String jwt;
    private Long userId;
    private String username;
    private String admin;
    private long expiration;

    public AuthResponse(String jwt, Long userId, String username, String admin, long expiration) {
        this.jwt = jwt;
        this.userId = userId;
        this.username = username;
        this.admin = admin;
        this.expiration = expiration;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
