package model;

/**
 * Clase que representa un usuario del sistema.
 * Puede ser un cliente, administrador u otro tipo de rol.
 */
public class Usuario {

    private int id;
    private String username;
    private String password;
    private String rol;

    /**
     * Constructor vacío necesario para mapeo y frameworks.
     */
    public Usuario() {}

    /**
     * Constructor sin ID (nuevo usuario).
     */
    public Usuario(String username, String password, String rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    /**
     * Constructor completo con ID (usuario ya registrado).
     */
    public Usuario(int id, String username, String password, String rol) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return String.format("Usuario{id=%d, username='%s', rol='%s'}", id, username, rol);
    }
}
