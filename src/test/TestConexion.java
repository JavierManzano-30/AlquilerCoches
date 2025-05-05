package test;

import java.sql.Connection;
import utils.Conexion;

public class TestConexion {

    public static void main(String[] args) {
        Connection conn = Conexion.getConexion();

        if (conn != null) {
            System.out.println("✅ Conexión exitosa a la base de datos.");
        } else {
            System.out.println("❌ No se pudo conectar.");
        }

        // Cerrar la conexión al final
        Conexion.cerrarConexion();
    }
}
