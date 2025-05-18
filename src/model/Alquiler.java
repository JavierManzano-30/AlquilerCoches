package model;

/**
 * Clase que representa un alquiler de coche dentro del sistema.
 * Contiene la información sobre el cliente, coche, fechas y precio total.
 */
public class Alquiler {
    private int id;
    private int idCliente;
    private int idCoche;
    private String fechaInicio;
    private int dias;
    private double total;

    /**
     * Constructor vacío.
     */
    public Alquiler() {}

    /**
     * Constructor para inserciones (sin ID).
     */
    public Alquiler(int idCliente, int idCoche, String fechaInicio, int dias, double total) {
        this.idCliente = idCliente;
        this.idCoche = idCoche;
        this.fechaInicio = fechaInicio;
        this.dias = dias;
        this.total = total;
    }

    /**
     * Constructor completo (con ID).
     */
    public Alquiler(int id, int idCliente, int idCoche, String fechaInicio, int dias, double total) {
        this.id = id;
        this.idCliente = idCliente;
        this.idCoche = idCoche;
        this.fechaInicio = fechaInicio;
        this.dias = dias;
        this.total = total;
    }

    // Getters
    public int getId() { return id; }
    public int getIdCliente() { return idCliente; }
    public int getIdCoche() { return idCoche; }
    public String getFechaInicio() { return fechaInicio; }
    public int getDias() { return dias; }
    public double getTotal() { return total; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public void setIdCoche(int idCoche) { this.idCoche = idCoche; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }
    public void setDias(int dias) { this.dias = dias; }
    public void setTotal(double total) { this.total = total; }

    @Override
    public String toString() {
        return "Alquiler{" +
                "id=" + id +
                ", idCliente=" + idCliente +
                ", idCoche=" + idCoche +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", dias=" + dias +
                ", total=" + total +
                '}';
    }
}
