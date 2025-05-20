package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Alquiler;
import model.Coche;
import model.DetalleAlquiler;
import controller.ConexionBD;

/**
 * DAO para gestionar operaciones sobre la entidad Alquiler.
 */
public class AlquilerDAO {

    public boolean crearAlquiler(Alquiler alquiler) {
        String sql = "INSERT INTO alquileres (id_cliente, id_coche, fecha_inicio, fecha_fin, total) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, alquiler.getIdCliente());
            stmt.setInt(2, alquiler.getIdCoche());
            stmt.setDate(3, Date.valueOf(alquiler.getFechaInicio()));
            stmt.setDate(4, Date.valueOf(alquiler.getFechaFin()));
            stmt.setDouble(5, alquiler.getTotal());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al crear alquiler: " + e.getMessage());
            return false;
        }
    }

    public List<Alquiler> listarAlquileres() {
        List<Alquiler> lista = new ArrayList<>();
        String sql = "SELECT * FROM alquileres";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Alquiler alquiler = new Alquiler(
                    rs.getInt("id"),
                    rs.getInt("id_cliente"),
                    rs.getInt("id_coche"),
                    rs.getDate("fecha_inicio").toLocalDate(),
                    rs.getDate("fecha_fin").toLocalDate(),
                    rs.getDouble("total")
                );
                lista.add(alquiler);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar alquileres: " + e.getMessage());
        }

        return lista;
    }

    public Alquiler buscarPorId(int id) {
        String sql = "SELECT * FROM alquileres WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Alquiler(
                        rs.getInt("id"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_coche"),
                        rs.getDate("fecha_inicio").toLocalDate(),
                        rs.getDate("fecha_fin").toLocalDate(),
                        rs.getDouble("total")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar alquiler: " + e.getMessage());
        }

        return null;
    }

    public boolean eliminarAlquiler(int id) {
        String sql = "DELETE FROM alquileres WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar alquiler: " + e.getMessage());
            return false;
        }
    }

    public List<DetalleAlquiler> detallesCliente(int idCliente) {
        String sql = """
            SELECT 
                c.id, ma.nombre AS marca, mo.nombre AS modelo, 
                c.anio, c.precio_dia, c.caballos, c.cilindrada, c.transmision,
                a.fecha_inicio, a.fecha_fin, a.total
            FROM alquileres a
            JOIN coches c ON a.id_coche = c.id
            JOIN modelo mo ON c.id_modelo = mo.id
            JOIN marca ma ON mo.id_marca = ma.id
            WHERE a.id_cliente = ?
        """;

        List<DetalleAlquiler> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Coche coche = new Coche(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("anio"),
                        rs.getDouble("precio_dia"),
                        true,
                        rs.getInt("caballos"),
                        rs.getInt("cilindrada"),
                        rs.getString("transmision")
                    );

                    java.sql.Date sqlInicio = rs.getDate("fecha_inicio");
                    java.sql.Date sqlFin = rs.getDate("fecha_fin");

                    LocalDate inicio = (sqlInicio != null) ? sqlInicio.toLocalDate() : null;
                    LocalDate fin = (sqlFin != null) ? sqlFin.toLocalDate() : null;

                    int dias = (inicio != null && fin != null)
                            ? (int) java.time.temporal.ChronoUnit.DAYS.between(inicio, fin) + 1
                            : 0;

                    double total = rs.getDouble("total");

                    DetalleAlquiler detalle = new DetalleAlquiler(coche, dias, total);
                    detalle.setFechaInicio(inicio);
                    detalle.setFechaFin(fin);

                    lista.add(detalle);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener detalles de cliente: " + e.getMessage());
        }

        return lista;
    }


    public boolean eliminarAlquilerPorCocheYClienteYFecha(int idCoche, int idCliente, LocalDate fechaInicio) {
        String sql = "DELETE FROM alquileres WHERE id_coche = ? AND id_cliente = ? AND fecha_inicio = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCoche);
            stmt.setInt(2, idCliente);
            stmt.setDate(3, Date.valueOf(fechaInicio));
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar alquiler por coche, cliente y fecha: " + e.getMessage());
            return false;
        }
    }

    public List<Alquiler> obtenerAlquileresCoche(int idCoche) {
        List<Alquiler> lista = new ArrayList<>();
        String sql = "SELECT * FROM alquileres WHERE id_coche = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCoche);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Alquiler alquiler = new Alquiler(
                        rs.getInt("id"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_coche"),
                        rs.getDate("fecha_inicio").toLocalDate(),
                        rs.getDate("fecha_fin").toLocalDate(),
                        rs.getDouble("total")
                    );
                    lista.add(alquiler);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener alquileres por coche: " + e.getMessage());
        }

        return lista;
    }
}
