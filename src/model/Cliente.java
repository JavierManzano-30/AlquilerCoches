package model;

/**
 * Clase que representa un cliente en el sistema de alquiler de coches.
 * Contiene información personal y de contacto del cliente.
 */
public class Cliente {

    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String password;

    /**
     * Constructor completo para instanciar un cliente existente (por ejemplo, desde base de datos).
     */
    public Cliente(int id, String nombre, String apellido, String email, String telefono, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
    }

    /**
     * Constructor vacío requerido para instancias por frameworks o DAOs.
     */
    public Cliente() {}

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.format("Cliente[id=%d, nombre=%s %s, email=%s, tel=%s]",
                id, nombre, apellido, email, telefono);
    }
}
