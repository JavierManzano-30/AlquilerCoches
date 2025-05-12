package model;

public class Coche {
    private int id;
    private String marca;
    private String modelo;
    private int anio;
    private double precio;
    private boolean disponible;
    private int caballos;
    private int cilindrada;

    // Constructor sin ID (por si el ID lo genera la base de datos)
    public Coche(String marca, String modelo, int anio, double precio) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
    }

    // Constructor con ID (para cuando recuperas un coche de la base de datos)
    public Coche(int id, String marca, String modelo, int anio, double precio) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
    }
    
    public Coche(int id, String marca, String modelo, int anio, double precio, boolean disponible, int caballos, int cilindrada) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
        this.disponible = disponible;
        this.caballos = caballos;
        this.cilindrada = cilindrada;
    }

 // Getters
    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAnio() {
        return anio;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public int getCaballos() {
        return caballos;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    // Para depuración
    @Override
    public String toString() {
        return "Coche [id=" + id + ", marca=" + marca + ", modelo=" + modelo + ", anio=" + anio + ", precio=" + precio + "]";
    }
}
