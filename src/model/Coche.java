package model;

/**
 * Clase que representa un coche dentro del sistema de alquiler.
 * Contiene atributos relacionados con las características del vehículo.
 */
public class Coche {

    private int id;
    private String marca;
    private String modelo;
    private int anio;
    private double precio;
    private boolean disponible;
    private int caballos;
    private int cilindrada;
    private String transmision;


    /**
     * Constructor vacío requerido por algunos frameworks y DAOs.
     */
    public Coche() {}

    /**
     * Constructor para inserciones sin ID aún asignado.
     */
    public Coche(String marca, String modelo, int anio, double precio) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
    }

    /**
     * Constructor para recuperaciones parciales desde la base de datos.
     */
    public Coche(int id, String marca, String modelo, int anio, double precio) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
    }

    /**
     * Constructor completo con todos los atributos.
     */
    public Coche(int id, String marca, String modelo, int anio, double precio, boolean disponible, int caballos, int cilindrada, String transmision) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
        this.disponible = disponible;
        this.caballos = caballos;
        this.cilindrada = cilindrada;
        this.transmision = transmision;
    }

    // Getters
    public int getId() { return id; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAnio() { return anio; }
    public double getPrecio() { return precio; }
    public boolean isDisponible() { return disponible; }
    public int getCaballos() { return caballos; }
    public int getCilindrada() { return cilindrada; }
    public String getTransmision() { return transmision; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setAnio(int anio) { this.anio = anio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public void setCaballos(int caballos) { this.caballos = caballos; }
    public void setCilindrada(int cilindrada) { this.cilindrada = cilindrada; }
    public void setTransmision(String transmision) { this.transmision = transmision; }

    @Override
    public String toString() {
        return String.format(
            "Coche{id=%d, marca='%s', modelo='%s', año=%d, precio=%.2f, disponible=%s, caballos=%d, cilindrada=%dcc, transmision=%s}",
            id, marca, modelo, anio, precio, disponible ? "Sí" : "No", caballos, cilindrada, transmision
        );
    }

}
