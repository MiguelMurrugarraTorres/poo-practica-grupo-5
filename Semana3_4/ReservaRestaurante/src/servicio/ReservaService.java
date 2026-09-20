package restaurante.servicio;

import restaurante.excepciones.MesaNoDisponibleException;
import restaurante.modelo.Reserva;

import java.util.ArrayList;
import java.util.List;

public class ReservaService {

    private List<Reserva> reservas;

    public ReservaService() {
        reservas = new ArrayList<>();
    }

    public void registrarReserva(Reserva nuevaReserva)
            throws MesaNoDisponibleException {

        for (Reserva reserva : reservas) {

            if (!reserva.isEstadoReserva()) {
                continue;
            }

            if (reserva.getMesa().getIdMesa()
                    == nuevaReserva.getMesa().getIdMesa()
                    &&
                reserva.getDetalle()
                       .getFechaReserva()
                       .equals(
                           nuevaReserva
                               .getDetalle()
                               .getFechaReserva()
                       )
                    &&
                nuevaReserva.getDetalle()
                             .getHoraInicio()
                             .isBefore(
                                 reserva.getDetalle()
                                        .getHoraFin()
                             )
                    &&
                nuevaReserva.getDetalle()
                             .getHoraFin()
                             .isAfter(
                                 reserva.getDetalle()
                                        .getHoraInicio()
                             )) {

                throw new MesaNoDisponibleException(
                        "La mesa "
                        + nuevaReserva.getMesa().getNumeroMesa()
                        + " ya está reservada en ese horario."
                );
            }
        }

        if (nuevaReserva.getDetalle()
                .getCantidadPersonas()
                > nuevaReserva.getMesa()
                              .getCapacidad()) {

            throw new MesaNoDisponibleException(
                    "La cantidad de personas "
                    + "supera la capacidad de la mesa."
            );
        }

        reservas.add(nuevaReserva);

        nuevaReserva.getMesa()
                .cambiarEstado(false);

        System.out.println(
                "Reserva "
                + nuevaReserva.getIdReserva()
                + " registrada correctamente."
        );
    }

    public List<Reserva> listarReservas() {
        return reservas;
    }
}