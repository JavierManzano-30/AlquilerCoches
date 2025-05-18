package model;

/**
 * Clase que representa una factura generada tras un alquiler.
 * Contiene información del alquiler asociado, fecha de emisión y total facturado.
 */
public class Factura {
    private int id;
    private int idAlquiler;
    private String fechaEmision;
    private double total;

    /**
     * Constructor vacío.
     */
    public Factura() {}

    /**
     * Constructor sin ID (para creación de nuevas facturas).
     */
    public Factura(int idAlquiler, String fechaEmision, double total) {
        this.idAlquiler = idAlquiler;
        this.fechaEmision = fechaEmision;
        this.total = total;
    }

    /**
     * Constructor completo con ID (para facturas recuperadas de la base de datos).
     */
    public Factura(int id, int idAlquiler, String fechaEmision, double total) {
        this.id = id;
        this.idAlquiler = idAlquiler;
        this.fechaEmision = fechaEmision;
        this.total = total;
    }

    // Getters
    public int getId() { return id; }
    public int getIdAlquiler() { return idAlquiler; }
    public String getFechaEmision() { return fechaEmision; }
    public double getTotal() { return total; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setIdAlquiler(int idAlquiler) { this.idAlquiler = idAlquiler; }
    public void setFechaEmision(String fechaEmision) { this.fechaEmision = fechaEmision; }
    public void setTotal(double total) { this.total = total; }

    @Override
    public String toString() {
        return "Factura{" +
                "id=" + id +
                ", idAlquiler=" + idAlquiler +
                ", fechaEmision='" + fechaEmision + '\'' +
                ", total=" + total +
                '}';
    }
}
