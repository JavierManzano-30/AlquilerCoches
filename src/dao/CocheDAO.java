package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Coche;
import controller.ConexionBD;

public class CocheDAO {

    public List<Coche> listarCoches() {
        List<Coche> coches = new ArrayList<>();
        String sql = """
            SELECT 
                c.id,
                ma.nombre AS marca,
                mo.nombre AS modelo,
                c.anio,
                c.precio_dia,
                c.disponible,
                c.caballos,
                c.cilindrada,
                c.transmision
            FROM coches c
            JOIN modelo mo ON c.id_modelo = mo.id
            JOIN marca ma ON mo.id_marca = ma.id
        """;

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Coche coche = new Coche(
                    rs.getInt("id"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getInt("anio"),
                    rs.getDouble("precio_dia"),
                    rs.getBoolean("disponible"),
                    rs.getInt("caballos"),
                    rs.getInt("cilindrada"),
                    rs.getString("transmision") // NUEVO CAMPO
                );
                coches.add(coche);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar coches: " + e.getMessage());
        }

        return coches;
    }

    public boolean marcarComoNoDisponible(int idCoche) {
        String sql = "UPDATE coches SET disponible = false WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCoche);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al marcar coche como no disponible: " + e.getMessage());
            return false;
        }
    }

    public boolean marcarComoDisponible(int idCoche) {
        String sql = "UPDATE coches SET disponible = true WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCoche);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al marcar coche como disponible: " + e.getMessage());
            return false;
        }
    }
}
