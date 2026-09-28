/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package servicio;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import modelo.EstadoMesa;
import modelo.Mesa;
import modelo.Reserva;
import modelo.Ubicacion;
import repositorio.MesaRepositorio;
import repositorio.ReservaRepositorio;

public class MesaService {

    public void registrarMesa(Mesa mesa) {
        if (buscarMesa(mesa.getNumeroMesa()) != null) {
            throw new IllegalArgumentException("Ya existe la mesa " + mesa.getNumeroMesa() + ".");
        }
        MesaRepositorio.guardar(mesa);
    }

    public Mesa buscarMesa(int numeroMesa) {
        return MesaRepositorio.buscarPorNumero(numeroMesa);
    }

    public List<Mesa> listarMesas() {
        return MesaRepositorio.listarMesas();
    }

    public List<Mesa> listarMesasDisponibles() {
        List<Mesa> disponibles = new ArrayList<>();
        for (Mesa m : MesaRepositorio.listarMesas()) {
            if (m.estaDisponible()) disponibles.add(m);
        }
        return disponibles;
    }

    public List<Ubicacion> listarUbicaciones() {
        return MesaRepositorio.listarUbicaciones();
    }

    public Mesa registrarMesa(int numero, int capacidad, String nombreUbicacion, boolean activa) {
        if (numero <= 0 || capacidad <= 0) {
            throw new IllegalArgumentException("El número y la capacidad deben ser mayores a 0.");
        }
        Ubicacion u = MesaRepositorio.buscarUbicacion(nombreUbicacion);
        if (u == null) {
            throw new IllegalArgumentException("La ubicación no existe.");
        }
        Mesa m = new Mesa(MesaRepositorio.siguienteId(), numero, capacidad, activa, u);
        registrarMesa(m);
        return m;
    }

    public void eliminarMesa(int numero) {
        Mesa m = buscarMesa(numero);
        if (m == null) {
            throw new IllegalArgumentException("La mesa " + numero + " no existe.");
        }
        for (Reserva r : ReservaRepositorio.listar()) {
            boolean vigente = r.isEstadoReserva() && !r.getFechaReserva().isBefore(LocalDate.now());
            if (vigente && r.incluye(m)) {
                throw new IllegalArgumentException(
                        "La mesa " + numero + " tiene reservas pendientes y no se puede eliminar.");
            }
        }
        MesaRepositorio.eliminar(m);
    }

    public List<MesaDisponibilidad> mesasParaReserva(String ubicacion, int personas,
            LocalDate fecha, LocalTime inicio, LocalTime fin) {

        List<MesaDisponibilidad> resultado = new ArrayList<>();
        for (Mesa m : MesaRepositorio.listarMesas()) {
            boolean mismaUbicacion = m.getUbicacion().getNombreUbicacion().equalsIgnoreCase(ubicacion);
            if (mismaUbicacion && m.getCapacidad() >= personas) {
                resultado.add(new MesaDisponibilidad(m, estadoDe(m, fecha, inicio, fin)));
            }
        }
        resultado.sort(Comparator.comparingInt(d -> d.getMesa().getNumeroMesa()));
        return resultado;
    }

    public EstadoMesa estadoDe(Mesa mesa, LocalDate fecha, LocalTime inicio, LocalTime fin) {
        if (!mesa.isEstado()) return EstadoMesa.INACTIVA;
        return estaOcupada(mesa, fecha, inicio, fin) ? EstadoMesa.RESERVADA : EstadoMesa.DISPONIBLE;
    }

    public boolean estaOcupada(Mesa mesa, LocalDate fecha, LocalTime inicio, LocalTime fin) {
        for (Reserva r : ReservaRepositorio.listar()) {
            if (r.seCruzaCon(fecha, inicio, fin) && r.incluye(mesa)) return true;
        }
        return false;
    }

    public static class MesaDisponibilidad {
        private final Mesa mesa;
        private final EstadoMesa estado;

        public MesaDisponibilidad(Mesa mesa, EstadoMesa estado) {
            this.mesa = mesa;
            this.estado = estado;
        }

        public Mesa getMesa() { return mesa; }
        public EstadoMesa getEstado() { return estado; }
    }
}
