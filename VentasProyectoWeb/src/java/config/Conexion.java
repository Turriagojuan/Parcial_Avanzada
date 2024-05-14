package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3307/bd_ventas";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection Conexion() throws SQLException {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            // Aquí puedes registrar o lanzar la excepción para manejarla en otro lugar
            throw new SQLException("Error al conectar a la base de datos", e);
        } finally {
            // Asegúrate de cerrar la conexión en caso de que se haya abierto
            if (conexion != null) {
                System.out.println("Conexión exitosa");
            }
        }
        return conexion;
    }
}
