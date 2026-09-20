package restaurante.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class DetalleReserva {

    private int idReserva;
    private int idMesa;
    private LocalDate fechaReserva;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private int cantidadPersonas;
    private String observacion;

    public DetalleReserva(
            int idReserva,
            int idMesa,
            LocalDate fechaReserva,
            LocalTime horaInicio,
            LocalTime horaFin,
            int cantidadPersonas,
            String observacion) {

        this.idReserva = idReserva;
        this.idMesa = idMesa;
        this.fechaReserva = fechaReserva;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.cantidadPersonas = cantidadPersonas;
        this.observacion = observacion;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public String getObservacion() {
        return observacion;
    }
}