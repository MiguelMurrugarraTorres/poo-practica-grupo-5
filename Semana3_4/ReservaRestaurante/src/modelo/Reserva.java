package restaurante.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {

    private int idReserva;
    private Cliente cliente;
    private Trabajador trabajador;
    private Mesa mesa;

    private DetalleReserva detalle;

    private boolean estadoReserva;

    public Reserva(
            int idReserva,
            Cliente cliente,
            Trabajador trabajador,
            Mesa mesa,
            LocalDate fechaReserva,
            LocalTime horaInicio,
            LocalTime horaFin,
            int cantidadPersonas,
            String observacion) {

        this.idReserva = idReserva;
        this.cliente = cliente;
        this.trabajador = trabajador;
        this.mesa = mesa;
        this.estadoReserva = true;

        // Composición
        this.detalle = new DetalleReserva(
                idReserva,
                mesa.getIdMesa(),
                fechaReserva,
                horaInicio,
                horaFin,
                cantidadPersonas,
                observacion
        );
    }

    public int getIdReserva() {
        return idReserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public DetalleReserva getDetalle() {
        return detalle;
    }

    public boolean isEstadoReserva() {
        return estadoReserva;
    }

    public void cancelar() {
        estadoReserva = false;
    }
}