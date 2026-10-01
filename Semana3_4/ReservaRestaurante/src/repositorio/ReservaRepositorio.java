/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import Conexion.Conexion;
import excepciones.ErrorBaseDatosException;
import modelo.Mesa;
import modelo.Reserva;

public class ReservaRepositorio {

    public static List<Reserva> listar() {
        String sql = "SELECT * FROM reserva";
        List<Reserva> resultado = new ArrayList<>();
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Reserva r = new Reserva(
                        rs.getInt("id_socio"),
                        rs.getInt("id_trabajador"),
                        rs.getDate("fecha_reserva").toLocalDate(),
                        rs.getTime("hora_inicio").toLocalTime(),
                        rs.getTime("hora_fin").toLocalTime(),
                        rs.getInt("cantidad_personas"),
                        rs.getString("observacion"));
                r.setIdReserva(rs.getInt("id_reserva"));
                if (!rs.getBoolean("estado_reserva")) r.cancelar();
                cargarMesas(r);
                resultado.add(r);
            }
        } catch (SQLException e) {
            throw new ErrorBaseDatosException("No se pudieron cargar las reservas.", e);
        }
        return resultado;
    }

    /** Guarda la reserva y, dentro de la misma transacción, sus mesas en detalle_reserva. */
    public static void guardar(Reserva reserva) {
        String sqlReserva = "INSERT INTO reserva "
                + "(id_socio, id_trabajador, fecha_reserva, hora_inicio, hora_fin, cantidad_personas, observacion, estado_reserva) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_reserva (id_reserva, id_mesa) VALUES (?, ?)";

        try (Connection con = Conexion.obtener()) {
            con.setAutoCommit(false);
            try {
                try (PreparedStatement ps = con.prepareStatement(sqlReserva, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, reserva.getIdSocio());
                    ps.setInt(2, reserva.getIdTrabajador());
                    ps.setDate(3, Date.valueOf(reserva.getFechaReserva()));
                    ps.setTime(4, Time.valueOf(reserva.getHoraInicio()));
                    ps.setTime(5, Time.valueOf(reserva.getHoraFin()));
                    ps.setInt(6, reserva.getCantidadPersonas());
                    ps.setString(7, reserva.getObservacion());
                    ps.setBoolean(8, reserva.isEstadoReserva());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) reserva.setIdReserva(keys.getInt(1));
                    }
                }
                try (PreparedStatement ps = con.prepareStatement(sqlDetalle)) {
                    for (Mesa m : reserva.getMesas()) {
                        ps.setInt(1, reserva.getIdReserva());
                        ps.setInt(2, m.getIdMesa());
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new ErrorBaseDatosException("No se pudo registrar la reserva.", e);
        }
    }

    private static void cargarMesas(Reserva reserva) {
        String sql = "SELECT id_mesa FROM detalle_reserva WHERE id_reserva = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, reserva.getIdReserva());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Mesa m = MesaRepositorio.buscarPorId(rs.getInt("id_mesa"));
                    if (m != null) reserva.agregarMesa(m);
                }
            }
        } catch (SQLException e) {
            throw new ErrorBaseDatosException("No se pudieron cargar las mesas de la reserva.", e);
        }
    }
}