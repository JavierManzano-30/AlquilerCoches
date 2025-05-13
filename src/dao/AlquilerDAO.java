package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.ConexionBD;
import model.Coche;
import model.DetalleAlquiler;

public class AlquilerDAO {

    private Connection conn;

    public AlquilerDAO(Connection conn) { this.conn = conn; }

    /**
     * Devuelve los detalles de alquiler de un cliente para la factura.
     */
    public List<DetalleAlquiler> detallesCliente(int idCliente) throws SQLException {
        String sql = """
            SELECT c.id, ma.nombre AS marca, mo.nombre AS modelo, c.año, c.precio_dia,
                   c.caballos, c.cilindrada, a.dias, a.total
            FROM alquileres a
            JOIN coches c      ON a.id_coche   = c.id
            JOIN modelo mo     ON c.id_modelo  = mo.id
            JOIN marca  ma     ON mo.id_marca  = ma.id
            WHERE a.id_cliente = ?
        """;
        List<DetalleAlquiler> lista = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Coche coche = new Coche(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("año"),
                        rs.getDouble("precio_dia"),
                        true, // disponible no relevante aquí
                        rs.getInt("caballos"),
                        rs.getInt("cilindrada"));
                int dias   = rs.getInt("dias");
                double tot = rs.getDouble("total");
                lista.add(new DetalleAlquiler(coche, dias, tot));
            }
        }
        return lista;
    }

    // Uso rápido
    public static List<DetalleAlquiler> obtener(int idCliente) {
        try (Connection c = ConexionBD.conectar()) {
            return new AlquilerDAO(c).detallesCliente(idCliente);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}