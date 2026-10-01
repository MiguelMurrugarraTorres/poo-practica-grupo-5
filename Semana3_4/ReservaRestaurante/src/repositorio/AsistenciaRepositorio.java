/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import Conexion.Conexion;
import excepciones.ErrorBaseDatosException;

public class AsistenciaRepositorio {

    /** Resultado de consultar la asistencia de hoy de un trabajador. */
    public static class AsistenciaHoy {
        public final LocalTime horaEntrada;
        public final LocalTime horaSalida;   // null si todavía no registró salida

        public AsistenciaHoy(LocalTime horaEntrada, LocalTime horaSalida) {
            this.horaEntrada = horaEntrada;
            this.horaSalida = horaSalida;
        }
    }

    /** Si hoy no hay fila de asistencia para este trabajador, la crea con la hora actual. */
    public static void registrarEntradaSiNoExiste(int idTrabajador) {
        String sql = "INSERT IGNORE INTO asistencia (id_trabajador, fecha, hora_entrada) VALUES (?, CURDATE(), CURTIME())";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idTrabajador);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ErrorBaseDatosException("No se pudo registrar la entrada.", e);
        }
    }

    public static AsistenciaHoy consultarHoy(int idTrabajador) {
        String sql = "SELECT hora_entrada, hora_salida FROM asistencia WHERE id_trabajador = ? AND fecha = CURDATE()";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idTrabajador);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return new AsistenciaHoy(null, null);
                Time salida = rs.getTime("hora_salida");
                return new AsistenciaHoy(
                        rs.getTime("hora_entrada").toLocalTime(),
                        salida == null ? null : salida.toLocalTime());
            }
        } catch (SQLException e) {
            throw new ErrorBaseDatosException("No se pudo consultar la asistencia.", e);
        }
    }

    public static void registrarSalida(int idTrabajador) {
        String sql = "UPDATE asistencia SET hora_salida = CURTIME() WHERE id_trabajador = ? AND fecha = CURDATE()";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idTrabajador);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ErrorBaseDatosException("No se pudo registrar la salida.", e);
        }
    }
}