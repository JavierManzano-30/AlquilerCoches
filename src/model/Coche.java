package model;

public class Coche {
    private int id;
    private String marca;
    private String modelo;
    private int año;
    private double precio;
    private boolean disponible;
    private int caballos;
    private int cilindrada;

    // Constructor
    public Coche(int id, String marca, String modelo, int año, double precio, boolean disponible, int caballos, int cilindrada) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.precio = precio;
        this.disponible = disponible;
        this.caballos = caballos;
        this.cilindrada = cilindrada;
    }

    // Getters
    public int getId() { return id; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAño() { return año; }
    public double getPrecio() { return precio; }
    public boolean isDisponible() { return disponible; }
    public int getCaballos() { return caballos; }
    public int getCilindrada() { return cilindrada; }
}
