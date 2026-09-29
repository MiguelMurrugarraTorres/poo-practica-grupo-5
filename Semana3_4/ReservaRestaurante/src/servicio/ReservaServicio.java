/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;


import java.time.LocalDate;
import java.time.LocalTime;
import excepciones.ClienteNoEncontradoException;
import excepciones.DatosInvalidosException;
import excepciones.MesaNoDisponibleException;
import modelo.Cliente;
import modelo.EstadoMesa;
import modelo.Mesa;
import modelo.Reserva;
import repositorio.ClienteRepositorio;
import repositorio.ReservaRepositorio;

public class ReservaServicio {

    private static final LocalTime APERTURA = LocalTime.of(8, 0);
    private static final LocalTime CIERRE   = LocalTime.of(22, 0);

    private final MesaService mesaService = new MesaService();

    public Reserva registrar(String dni, LocalDate fecha, LocalTime inicio, LocalTime fin,
            int personas, String observacion, Mesa mesa, int idTrabajador)
            throws ClienteNoEncontradoException, DatosInvalidosException, MesaNoDisponibleException {

        Cliente cliente = ClienteRepositorio.buscarPorDni(dni);
        if (cliente == null) {
            throw new ClienteNoEncontradoException("No existe un cliente con el DNI " + dni + ".");
        }
        if (fecha.isBefore(LocalDate.now())) {
            throw new DatosInvalidosException("La fecha no puede ser anterior a hoy.");
        }
        if (fecha.equals(LocalDate.now()) && inicio.isBefore(LocalTime.now())) {
            throw new DatosInvalidosException("La hora de inicio no puede ser anterior a la hora actual.");
        }
        if (inicio.isBefore(APERTURA) || inicio.isAfter(CIERRE)) {
            throw new DatosInvalidosException(
                    "El horario de atención es de " + APERTURA + " a " + CIERRE + ".");
        }
        if (fin.isAfter(CIERRE)) {
            throw new DatosInvalidosException("La hora de fin no puede pasar de las " + CIERRE + " (cierre del local).");
        }
        if (!fin.isAfter(inicio)) {
            throw new DatosInvalidosException("La hora de fin debe ser posterior a la de inicio.");
        }
        if (personas <= 0) {
            throw new DatosInvalidosException("La cantidad de personas debe ser mayor a 0.");
        }
        if (mesa == null) {
            throw new DatosInvalidosException("Selecciona una mesa.");
        }
        if (personas > mesa.getCapacidad()) {
            throw new DatosInvalidosException("La mesa " + mesa.getNumeroMesa()
                    + " solo tiene capacidad para " + mesa.getCapacidad() + " personas.");
        }
        if (mesaService.estadoDe(mesa, fecha, inicio, fin) != EstadoMesa.DISPONIBLE) {
            throw new MesaNoDisponibleException("La mesa " + mesa.getNumeroMesa()
                    + " ya no está disponible en ese horario.");
        }

        Reserva r = new Reserva(cliente.getIdSocio(), idTrabajador, fecha, inicio, fin, personas, observacion);
        r.agregarMesa(mesa);
        ReservaRepositorio.guardar(r);
        return r;
    }
}