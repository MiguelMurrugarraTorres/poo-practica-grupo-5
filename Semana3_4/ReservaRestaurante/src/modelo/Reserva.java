/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reserva {

    private int idReserva;
    private final int idSocio;
    private final int idTrabajador;
    private final LocalDate fechaReserva;
    private final LocalTime horaInicio;
    private final LocalTime horaFin;
    private final int cantidadPersonas;
    private final String observacion;
    private boolean estadoReserva = true;
    private final List<Mesa> mesas = new ArrayList<>();

    public Reserva(int idSocio, int idTrabajador, LocalDate fechaReserva,
                   LocalTime horaInicio, LocalTime horaFin,
                   int cantidadPersonas, String observacion) {
        this.idSocio = idSocio;
        this.idTrabajador = idTrabajador;
        this.fechaReserva = fechaReserva;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.cantidadPersonas = cantidadPersonas;
        this.observacion = observacion;
    }

    public int getIdReserva() { return idReserva; }
    public void setIdReserva(int idReserva) { this.idReserva = idReserva; }
    public int getIdSocio() { return idSocio; }
    public int getIdTrabajador() { return idTrabajador; }
    public LocalDate getFechaReserva() { return fechaReserva; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public int getCantidadPersonas() { return cantidadPersonas; }
    public String getObservacion() { return observacion; }
    public boolean isEstadoReserva() { return estadoReserva; }
    public void cancelar() { this.estadoReserva = false; }

    public void agregarMesa(Mesa mesa) { mesas.add(mesa); }
    public List<Mesa> getMesas() { return Collections.unmodifiableList(mesas); }

    public boolean incluye(Mesa mesa) {
        for (Mesa m : mesas) {
            if (m.getIdMesa() == mesa.getIdMesa()) return true;
        }
        return false;
    }

    public boolean seCruzaCon(LocalDate fecha, LocalTime inicio, LocalTime fin) {
        return estadoReserva
                && fechaReserva.equals(fecha)
                && horaInicio.isBefore(fin)
                && horaFin.isAfter(inicio);
    }
}
