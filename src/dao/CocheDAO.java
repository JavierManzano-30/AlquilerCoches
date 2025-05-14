package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Coche;

public class CocheDAO {
    
    private Connection conexion;

    public CocheDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<Coche> obtenerTodosLosCoches() throws SQLException {
        List<Coche> coches = new ArrayList<>();
        String sql = "SELECT c.id AS coche_id, m.nombre AS marca, mo.nombre AS modelo, c.año, c.precio_dia, c.disponible, c.caballos, c.cilindrada "
                   + "FROM coches c "
                   + "JOIN modelo mo ON c.id_modelo = mo.id "
                   + "JOIN marca m ON mo.id_marca = m.id";

        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Coche coche = new Coche(
                    rs.getInt("coche_id"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getInt("año"),
                    rs.getDouble("precio_dia"),
                    rs.getBoolean("disponible"),
                    rs.getInt("caballos"),
                    rs.getInt("cilindrada")
                );
                coches.add(coche);
            }
        }
        return coches;
    }

    public Coche obtenerCochePorId(int id) throws SQLException {
        String sql = "SELECT c.id AS coche_id, m.nombre AS marca, mo.nombre AS modelo, " +
                     "c.año, c.precio_dia, c.disponible, c.caballos, c.cilindrada " +
                     "FROM coches c " +
                     "JOIN modelo mo ON c.id_modelo = mo.id " +
                     "JOIN marca m ON mo.id_marca = m.id " +
                     "WHERE c.id = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Coche(
                        rs.getInt("coche_id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("año"),
                        rs.getDouble("precio_dia"),
                        rs.getBoolean("disponible"),
                        rs.getInt("caballos"),
                        rs.getInt("cilindrada")
                    );
                }
            }
        }
        return null;
    }


    public void insertarCoche(Coche coche) throws SQLException {
        String sql = "INSERT INTO coches (marca, modelo, anio, precio) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, coche.getMarca());
            stmt.setString(2, coche.getModelo());
            stmt.setInt(3, coche.getAnio());
            stmt.setDouble(4, coche.getPrecio());
            stmt.executeUpdate();
        }
    }

    public void actualizarCoche(Coche coche) throws SQLException {
        String sql = "UPDATE coches SET marca = ?, modelo = ?, anio = ?, precio = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, coche.getMarca());
            stmt.setString(2, coche.getModelo());
            stmt.setInt(3, coche.getAnio());
            stmt.setDouble(4, coche.getPrecio());
            stmt.setInt(5, coche.getId());
            stmt.executeUpdate();
        }
    }

    public void eliminarCoche(int id) throws SQLException {
        String sql = "DELETE FROM coches WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
