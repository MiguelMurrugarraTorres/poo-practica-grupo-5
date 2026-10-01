/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Conexion.Conexion;
import excepciones.ErrorBaseDatosException;
import modelo.Trabajador;

public class TrabajadorRepositorio {

    public static Trabajador buscarPorCorreo(String correo) {
        String sql = "SELECT * FROM trabajador WHERE correo = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new Trabajador(
                        rs.getInt("id_trabajador"),
                        rs.getString("dni"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("contrasena"),
                        rs.getString("cargo"),
                        rs.getString("turno"));
            }
        } catch (SQLException e) {
            throw new ErrorBaseDatosException("No se pudo buscar el trabajador.", e);
        }
    }
}