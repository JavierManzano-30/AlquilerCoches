package model;

public class Coche {
    private int id;
    private int idModelo;
    private int año;
    private int caballos;
    private int cilindrada;
    private double precioDia;
    private boolean disponible;

    // Constructor vacío
    public Coche() {}

    // Constructor completo
    public Coche(int id, int idModelo, int año, int caballos, int cilindrada, double precioDia, boolean disponible) {
        this.id = id;
        this.idModelo = idModelo;
        this.año = año;
        this.caballos = caballos;
        this.cilindrada = cilindrada;
        this.precioDia = precioDia;
        this.disponible = disponible;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(int idModelo) {
        this.idModelo = idModelo;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public int getCaballos() {
        return caballos;
    }

    public void setCaballos(int caballos) {
        this.caballos = caballos;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    public double getPrecioDia() {
        return precioDia;
    }

    public void setPrecioDia(double precioDia) {
        this.precioDia = precioDia;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
