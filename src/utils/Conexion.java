package utils;

import java.sql.*;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/alquiler_coches_japoneses";
    private static final String USER = "root";
    private static final String PASSWORD = "calvoperilloso30";

    private static Connection conexion = null;

    public static Connection getConexion() {
        if (conexion == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión establecida correctamente.");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return conexion;
    }

    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
