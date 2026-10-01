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
import excepciones.DatosInvalidosException;
import excepciones.MesaConReservaException;
import excepciones.MesaNoEncontradaException;
import excepciones.MesaYaExistenteException;
import modelo.EstadoMesa;
import modelo.Mesa;
import modelo.Reserva;
import modelo.Ubicacion;
import repositorio.MesaRepositorio;
import repositorio.ReservaRepositorio;

public class MesaService {

    public void registrarMesa(Mesa mesa) throws MesaYaExistenteException {
        if (buscarMesa(mesa.getNumeroMesa()) != null) {
            throw new MesaYaExistenteException("Ya existe la mesa " + mesa.getNumeroMesa() + ".");
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

    public Mesa registrarMesa(int numero, int capacidad, String nombreUbicacion, boolean activa)
        throws DatosInvalidosException, MesaYaExistenteException {
    if (numero <= 0 || capacidad <= 0) {
        throw new DatosInvalidosException("El número y la capacidad deben ser mayores a 0.");
    }
    Ubicacion u = MesaRepositorio.buscarUbicacion(nombreUbicacion);
    if (u == null) {
        throw new DatosInvalidosException("La ubicación no existe.");
    }
    Mesa m = new Mesa(0, numero, capacidad, activa, u);   // el id real lo asigna MySQL al guardar
    registrarMesa(m);
    return m;
}

    public void eliminarMesa(int numero) throws MesaNoEncontradaException, MesaConReservaException {
        Mesa m = buscarMesa(numero);
        if (m == null) {
            throw new MesaNoEncontradaException("La mesa " + numero + " no existe.");
        }
        for (Reserva r : ReservaRepositorio.listar()) {
            boolean vigente = r.isEstadoReserva() && !r.getFechaReserva().isBefore(LocalDate.now());
            if (vigente && r.incluye(m)) {
                throw new MesaConReservaException(
                        "La mesa " + numero + " tiene reservas pendientes y no se puede eliminar.");
            }
        }
        MesaRepositorio.eliminar(m);
    }

    /**
     * Capacidad exacta que corresponde a "personas", mirando TODAS las mesas
     * del restaurante (sin importar ubicación): si existe una mesa con
     * capacidad == personas, se usa esa. Si no existe, se prueba con
     * personas + 1, luego personas + 2, y así hasta encontrar una capacidad
     * que sí exista en alguna mesa.
     *
     *   1 persona, sin mesas de 1 pero sí de 2  → capacidad 2
     *   4 personas, con mesas de 4               → capacidad 4 (no sube a 6 u 8)
     *   5 personas, solo mesas de 4, 6, 8         → capacidad 6
     */
    private int capacidadRequerida(int personas) {
        for (int capacidad = personas; capacidad <= 100; capacidad++) {
            final int c = capacidad;
            boolean existe = MesaRepositorio.listarMesas().stream()
                    .anyMatch(m -> m.getCapacidad() == c);
            if (existe) return capacidad;
        }
        return personas; // no hay ninguna mesa que alcance en todo el sistema
    }

    /**
     * Mesas de una ubicación para una reserva de "personas" personas:
     * solo las que tienen exactamente la capacidad que corresponde a esa
     * cantidad (calculada sobre todo el restaurante, no solo esa ubicación).
     * Si esa ubicación no tiene ninguna mesa de esa capacidad exacta, no
     * muestra ninguna.
     */
    public List<MesaDisponibilidad> mesasParaReserva(String ubicacion, int personas,
            LocalDate fecha, LocalTime inicio, LocalTime fin) {

        int capacidadRequerida = capacidadRequerida(personas);

        List<MesaDisponibilidad> resultado = new ArrayList<>();
        for (Mesa m : MesaRepositorio.listarMesas()) {
            boolean mismaUbicacion = m.getUbicacion().getNombreUbicacion().equalsIgnoreCase(ubicacion);
            if (mismaUbicacion && m.getCapacidad() == capacidadRequerida) {
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