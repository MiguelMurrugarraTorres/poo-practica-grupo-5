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
import java.util.ArrayList;
import java.util.List;
import Conexion.Conexion;
import excepciones.ErrorBaseDatosException;
import modelo.Mesa;
import modelo.Ubicacion;

public class MesaRepositorio {

    public static List<Ubicacion> listarUbicaciones() {
        String sql = "SELECT * FROM ubicacion ORDER BY id_ubicacion";
        List<Ubicacion> resultado = new ArrayList<>();
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) resultado.add(mapearUbicacion(rs));
        } catch (SQLException e) {
            throw new ErrorBaseDatosException("No se pudieron cargar las ubicaciones.", e);
        }
        return resultado;
    }

    public static Ubicacion buscarUbicacion(String nombre) {
        String sql = "SELECT * FROM ubicacion WHERE nombre_ubicacion = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapearUbicacion(rs) : null;
            }
        } catch (SQLException e) {
            throw new ErrorBaseDatosException("No se pudo buscar la ubicación.", e);
        }
    }

    public static List<Mesa> listarMesas() {
        String sql = "SELECT m.id_mesa, m.numero_mesa, m.capacidad, m.estado, "
                   + "u.id_ubicacion, u.nombre_ubicacion, u.acepta_mascotas "
                   + "FROM mesa m JOIN ubicacion u ON m.id_ubicacion = u.id_ubicacion "
                   + "ORDER BY m.numero_mesa";
        List<Mesa> resultado = new ArrayList<>();
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) resultado.add(mapearMesa(rs));
        } catch (SQLException e) {
            throw new ErrorBaseDatosException("No se pudieron cargar las mesas.", e);
        }
        return resultado;
    }

    public static Mesa buscarPorNumero(int numero) {
        String sql = "SELECT m.id_mesa, m.numero_mesa, m.capacidad, m.estado, "
                   + "u.id_ubicacion, u.nombre_ubicacion, u.acepta_mascotas "
                   + "FROM mesa m JOIN ubicacion u ON m.id_ubicacion = u.id_ubicacion "
                   + "WHERE m.numero_mesa = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numero);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapearMesa(rs) : null;
            }
        } catch (SQLException e) {
            throw new ErrorBaseDatosException("No se pudo buscar la mesa.", e);
        }
    }

    public static Mesa buscarPorId(int idMesa) {
        String sql = "SELECT m.id_mesa, m.numero_mesa, m.capacidad, m.estado, "
                   + "u.id_ubicacion, u.nombre_ubicacion, u.acepta_mascotas "
                   + "FROM mesa m JOIN ubicacion u ON m.id_ubicacion = u.id_ubicacion "
                   + "WHERE m.id_mesa = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMesa);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapearMesa(rs) : null;
            }
        } catch (SQLException e) {
            throw new ErrorBaseDatosException("No se pudo buscar la mesa.", e);
        }
    }

    /** Inserta la mesa y deja el id real (generado por MySQL) escrito en el propio objeto. */
    public static void guardar(Mesa mesa) {
        String sql = "INSERT INTO mesa (numero_mesa, capacidad, estado, id_ubicacion) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, mesa.getNumeroMesa());
            ps.setInt(2, mesa.getCapacidad());
            ps.setBoolean(3, mesa.isEstado());
            ps.setInt(4, mesa.getUbicacion().getIdUbicacion());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) mesa.setIdMesa(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new ErrorBaseDatosException("No se pudo registrar la mesa.", e);
        }
    }

    public static void eliminar(Mesa mesa) {
        String sql = "DELETE FROM mesa WHERE id_mesa = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, mesa.getIdMesa());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ErrorBaseDatosException("No se pudo eliminar la mesa.", e);
        }
    }

    private static Ubicacion mapearUbicacion(ResultSet rs) throws SQLException {
        return new Ubicacion(
                rs.getInt("id_ubicacion"),
                rs.getString("nombre_ubicacion"),
                rs.getBoolean("acepta_mascotas"));
    }

    private static Mesa mapearMesa(ResultSet rs) throws SQLException {
        Ubicacion u = mapearUbicacion(rs);
        return new Mesa(
                rs.getInt("id_mesa"),
                rs.getInt("numero_mesa"),
                rs.getInt("capacidad"),
                rs.getBoolean("estado"),
                u);
    }
}