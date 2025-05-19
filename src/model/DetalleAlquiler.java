package model;

import java.time.LocalDate;

/**
 * Representa un detalle de alquiler de un coche, incluyendo:
 * - El coche alquilado
 * - La cantidad de días del alquiler
 * - Las fechas de inicio y fin del alquiler
 * - El costo total del alquiler
 */
public class DetalleAlquiler {

    private Coche coche;
    private int dias;
    private double total;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    /**
     * Constructor principal sin fechas.
     */
    public DetalleAlquiler(Coche coche, int dias, double total) {
        this.coche = coche;
        this.dias = dias;
        this.total = total;
    }

    /**
     * Constructor con fechas.
     */
    public DetalleAlquiler(Coche coche, int dias, double total, LocalDate fechaInicio, LocalDate fechaFin) {
        this.coche = coche;
        this.dias = dias;
        this.total = total;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
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

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
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

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return String.format("DetalleAlquiler[coche=%s, dias=%d, total=%.2f €, desde=%s, hasta=%s]",
                coche != null ? coche.getModelo() : "null",
                dias, total,
                fechaInicio != null ? fechaInicio : "¿?",
                fechaFin != null ? fechaFin : "¿?");
    }
}
