package model;

/**
 * Representa un detalle de alquiler de un coche, incluyendo:
 * - El coche alquilado
 * - La cantidad de días del alquiler
 * - El costo total del alquiler
 */
public class DetalleAlquiler {

    private Coche coche;
    private int dias;
    private double total;

    /**
     * Constructor principal.
     *
     * @param coche Objeto {@link Coche} alquilado
     * @param dias  Número de días del alquiler
     * @param total Costo total del alquiler
     */
    public DetalleAlquiler(Coche coche, int dias, double total) {
        this.coche = coche;
        this.dias = dias;
        this.total = total;
    }

    // Getters
    public Coche getCoche() {
        return coche;
    }

    public int getDias() {
        return dias;
    }

    public double getTotal() {
        return total;
    }

    // Setters
    public void setCoche(Coche coche) {
        this.coche = coche;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return String.format("DetalleAlquiler[coche=%s, dias=%d, total=%.2f €]",
                coche != null ? coche.getModelo() : "null", dias, total);
    }
}
