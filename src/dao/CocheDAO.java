package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Coche;
import utils.Conexion;

public class CocheDAO {

    private Connection conn;

    public CocheDAO() {
        conn = Conexion.getConexion(); // Usa tu clase de conexión aquí
    }

    // Crear nuevo coche
    public boolean insertarCoche(Coche coche) {
        String sql = "INSERT INTO coches (id_modelo, año, caballos, cilindrada, precio_dia, disponible) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, coche.getIdModelo());
            stmt.setInt(2, coche.getAño());
            stmt.setInt(3, coche.getCaballos());
            stmt.setInt(4, coche.getCilindrada());
            stmt.setDouble(5, coche.getPrecioDia());
            stmt.setBoolean(6, coche.isDisponible());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Leer todos los coches
    public List<Coche> obtenerTodosLosCoches() {
        List<Coche> lista = new ArrayList<>();
        String sql = "SELECT * FROM coches";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Coche c = new Coche();
                c.setId(rs.getInt("id"));
                c.setIdModelo(rs.getInt("id_modelo"));
                c.setAño(rs.getInt("año"));
                c.setCaballos(rs.getInt("caballos"));
                c.setCilindrada(rs.getInt("cilindrada"));
                c.setPrecioDia(rs.getDouble("precio_dia"));
                c.setDisponible(rs.getBoolean("disponible"));
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Actualizar un coche
    public boolean actualizarCoche(Coche coche) {
        String sql = "UPDATE coches SET id_modelo = ?, año = ?, caballos = ?, cilindrada = ?, precio_dia = ?, disponible = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, coche.getIdModelo());
            stmt.setInt(2, coche.getAño());
            stmt.setInt(3, coche.getCaballos());
            stmt.setInt(4, coche.getCilindrada());
            stmt.setDouble(5, coche.getPrecioDia());
            stmt.setBoolean(6, coche.isDisponible());
            stmt.setInt(7, coche.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar coche
    public boolean eliminarCoche(int id) {
        String sql = "DELETE FROM coches WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
