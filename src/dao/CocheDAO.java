
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Coche;
import utils.Conexion;

/**
 * Clase DAO para gestionar operaciones CRUD sobre la entidad Coche.
 */
public class CocheDAO {

    /**
     * Lista todos los coches registrados en el sistema.
     */
    public List<Coche> listarCoches() {
        List<Coche> coches = new ArrayList<>();
        String sql = "SELECT * FROM coches";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Coche coche = new Coche(
                    rs.getInt("id"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getInt("anio"),
                    rs.getDouble("precio"),
                    rs.getBoolean("disponible"),
                    rs.getInt("caballos"),
                    rs.getInt("cilindrada")
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
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCoche);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al marcar coche como no disponible: " + e.getMessage());
            return false;
        }
    }


    // Resto de métodos se mantiene igual (insertar, actualizar, eliminar)...
}
