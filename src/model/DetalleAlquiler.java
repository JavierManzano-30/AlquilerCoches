package model;

public class DetalleAlquiler {
    private Coche coche;
    private int dias;
    private double total;

    public DetalleAlquiler(Coche coche, int dias, double total) {
        this.coche = coche;
        this.dias = dias;
        this.total = total;
    }

    public Coche getCoche() { return coche; }
    public int getDias() { return dias; }
    public double getTotal() { return total; }
}