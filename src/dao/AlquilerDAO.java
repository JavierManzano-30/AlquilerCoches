package dao;

import java.sql.*;
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
        String sql = "INSERT INTO alquileres (id_cliente, id_coche, fecha_inicio, dias, total) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, alquiler.getIdCliente());
            stmt.setInt(2, alquiler.getIdCoche());
            stmt.setString(3, alquiler.getFechaInicio());
            stmt.setInt(4, alquiler.getDias());
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
                    rs.getString("fecha_inicio"),
                    rs.getInt("dias"),
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
                        rs.getString("fecha_inicio"),
                        rs.getInt("dias"),
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
                a.fecha_inicio, a.dias, a.total
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
                        true, // disponible no aplica aquí
                        rs.getInt("caballos"),
                        rs.getInt("cilindrada"),
                        rs.getString("transmision") // NUEVO CAMPO
                    );
                    int dias = rs.getInt("dias");
                    double total = rs.getDouble("total");

                    lista.add(new DetalleAlquiler(coche, dias, total));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener detalles de cliente: " + e.getMessage());
        }

        return lista;
    }

    public boolean eliminarAlquilerPorCocheYCliente(int idCoche, int idCliente) {
        String sql = "DELETE FROM alquileres WHERE id_coche = ? AND id_cliente = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCoche);
            stmt.setInt(2, idCliente);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar alquiler por coche y cliente: " + e.getMessage());
            return false;
        }
    }
}
