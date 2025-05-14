package test;

import java.sql.Connection;
import utils.Conexion;

/**
 * Clase de prueba para verificar que la conexión a la base de datos es correcta.
 */
public class TestConexion {

    public static void main(String[] args) {
        try (Connection conn = Conexion.getConexion()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Conexión exitosa a la base de datos.");
            } else {
                System.out.println("❌ La conexión no se pudo establecer.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error al conectar con la base de datos:");
            e.printStackTrace();
        }
    }
}
