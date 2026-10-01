/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    
    private static final String URL = "jdbc:mysql://localhost:3306/reserva_restaurante?useSSL=false&serverTimezone=America/Lima";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "2006";

    private Conexion() { }

  
    public static Connection obtener() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL. Verifica que el .jar del conector esté en Libraries.", e);
        }
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}