package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Alquiler;
import model.Coche;
import model.DetalleAlquiler;
import utils.Conexion;

/**
 * DAO para gestionar operaciones sobre la entidad Alquiler.
 */
public class AlquilerDAO {

    public boolean crearAlquiler(Alquiler alquiler) {
        String sql = "INSERT INTO alquileres (id_cliente, id_coche, fecha_inicio, fecha_fin, total) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, alquiler.getIdCliente());
            stmt.setInt(2, alquiler.getIdCoche());
            stmt.setString(3, alquiler.getFechaInicio());
            stmt.setString(4, alquiler.getFechaFin());
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

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Alquiler alquiler = new Alquiler(
                    rs.getInt("id"),
                    rs.getInt("id_cliente"),
                    rs.getInt("id_coche"),
                    rs.getString("fecha_inicio"),
                    rs.getString("fecha_fin"),
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

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Alquiler(
                        rs.getInt("id"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_coche"),
                        rs.getString("fecha_inicio"),
                        rs.getString("fecha_fin"),
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

        try (Connection conn = Conexion.getConexion();
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
            SELECT c.id, c.marca, c.modelo, c.anio, c.precio, c.caballos, c.cilindrada,
                   a.fecha_inicio, a.fecha_fin, a.total, DATEDIFF(a.fecha_fin, a.fecha_inicio) AS dias
            FROM alquileres a
            JOIN coches c ON a.id_coche = c.id
            WHERE a.id_cliente = ?
        """;

        List<DetalleAlquiler> lista = new ArrayList<>();

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Coche coche = new Coche(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("anio"),
                        rs.getDouble("precio"),
                        true, // disponible no aplica aquí
                        rs.getInt("caballos"),
                        rs.getInt("cilindrada")
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
}
