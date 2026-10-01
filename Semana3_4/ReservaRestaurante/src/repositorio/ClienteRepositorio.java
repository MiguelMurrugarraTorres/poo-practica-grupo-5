/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Conexion.Conexion;
import excepciones.ErrorBaseDatosException;
import modelo.Cliente;

public class ClienteRepositorio {

    public static Cliente buscarPorDni(String dni) {
        String sql = "SELECT * FROM cliente WHERE dni = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        } catch (SQLException e) {
            throw new ErrorBaseDatosException("No se pudo buscar el cliente.", e);
        }
    }

    public static void guardar(Cliente cliente) {
        String sql = "INSERT INTO cliente (dni, nombres, apellidos, telefono, correo) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombres());
            ps.setString(3, cliente.getApellidos());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getCorreo());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) cliente.setIdSocio(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new ErrorBaseDatosException("No se pudo registrar el cliente.", e);
        }
    }

    private static Cliente mapear(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("id_socio"),
                rs.getString("dni"),
                rs.getString("nombres"),
                rs.getString("apellidos"),
                rs.getString("telefono"),
                rs.getString("correo"));
    }
}