/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import modelo.Reserva;

public class ReservaRepositorio {

    private static final List<Reserva> RESERVAS = new ArrayList<>();
    private static int contador = 1;

    static {
        demo(LocalTime.of(12, 0), LocalTime.of(15, 0), 1, 4);
        demo(LocalTime.of(12, 0), LocalTime.of(15, 0), 7, 9, 11);
    }

    private static void demo(LocalTime ini, LocalTime fin, int... numerosMesa) {
        Reserva r = new Reserva(1, 1, LocalDate.now(), ini, fin, 4, "Reserva de ejemplo");
        for (int n : numerosMesa) r.agregarMesa(MesaRepositorio.buscarPorNumero(n));
        guardar(r);
    }

    public static List<Reserva> listar() { return new ArrayList<>(RESERVAS); }

    public static void guardar(Reserva reserva) {
        reserva.setIdReserva(contador++);
        RESERVAS.add(reserva);
    }
}