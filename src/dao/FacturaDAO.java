package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Factura;
import utils.Conexion;

/**
 * DAO para gestionar operaciones CRUD sobre la entidad Factura.
 */
public class FacturaDAO {

    /**
     * Inserta una nueva factura en la base de datos.
     */
    public boolean crearFactura(Factura factura) {
        String sql = "INSERT INTO facturas (id_alquiler, fecha_emision, total) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, factura.getIdAlquiler());
            stmt.setString(2, factura.getFechaEmision());
            stmt.setDouble(3, factura.getTotal());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al crear factura: " + e.getMessage());
            return false;
        }
    }

    /**
     * Recupera una factura por su ID.
     */
    public Factura buscarPorId(int id) {
        String sql = "SELECT * FROM facturas WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Factura(
                    rs.getInt("id"),
                    rs.getInt("id_alquiler"),
                    rs.getString("fecha_emision"),
                    rs.getDouble("total")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar factura: " + e.getMessage());
        }

        return null;
    }

    /**
     * Lista todas las facturas registradas.
     */
    public List<Factura> listarFacturas() {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT * FROM facturas";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Factura factura = new Factura(
                    rs.getInt("id"),
                    rs.getInt("id_alquiler"),
                    rs.getString("fecha_emision"),
                    rs.getDouble("total")
                );
                facturas.add(factura);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar facturas: " + e.getMessage());
        }

        return facturas;
    }

    /**
     * Elimina una factura por su ID.
     */
    public boolean eliminarFactura(int id) {
        String sql = "DELETE FROM facturas WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar factura: " + e.getMessage());
            return false;
        }
    }
}
