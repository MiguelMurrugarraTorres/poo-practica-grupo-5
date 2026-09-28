/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import java.time.LocalDate;
import java.time.LocalTime;
import modelo.Cliente;
import modelo.EstadoMesa;
import modelo.Mesa;
import modelo.Reserva;
import repositorio.ClienteRepositorio;
import repositorio.ReservaRepositorio;

public class ReservaServicio {

    private final MesaService mesaService = new MesaService();

    public Reserva registrar(String dni, LocalDate fecha, LocalTime inicio, LocalTime fin,
            int personas, String observacion, Mesa mesa, int idTrabajador) {

        Cliente cliente = ClienteRepositorio.buscarPorDni(dni);
        if (cliente == null) {
            throw new IllegalArgumentException("No existe un cliente con el DNI " + dni + ".");
        }
        if (fecha.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser anterior a hoy.");
        }
        if (!fin.isAfter(inicio)) {
            throw new IllegalArgumentException("La hora de fin debe ser posterior a la de inicio.");
        }
        if (personas <= 0) {
            throw new IllegalArgumentException("La cantidad de personas debe ser mayor a 0.");
        }
        if (mesa == null) {
            throw new IllegalArgumentException("Selecciona una mesa.");
        }
        if (personas > mesa.getCapacidad()) {
            throw new IllegalArgumentException("La mesa " + mesa.getNumeroMesa()
                    + " solo tiene capacidad para " + mesa.getCapacidad() + " personas.");
        }
        if (mesaService.estadoDe(mesa, fecha, inicio, fin) != EstadoMesa.DISPONIBLE) {
            throw new IllegalArgumentException("La mesa " + mesa.getNumeroMesa()
                    + " ya no está disponible en ese horario.");
        }

        Reserva r = new Reserva(cliente.getIdSocio(), idTrabajador, fecha, inicio, fin, personas, observacion);
        r.agregarMesa(mesa);
        ReservaRepositorio.guardar(r);
        return r;
    }
}
