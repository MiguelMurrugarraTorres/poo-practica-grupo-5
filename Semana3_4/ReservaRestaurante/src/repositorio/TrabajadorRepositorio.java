/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;
import java.util.ArrayList;
import java.util.List;
import modelo.Trabajador;
/**
 *
 * @author Diego
 */
public class TrabajadorRepositorio {
 private static final List<Trabajador> TRABAJADORES = new ArrayList<>();

    static {
        TRABAJADORES.add(new Trabajador(1, "70011122", "Diego", "Salazar",
                "988111222", "diego@restaurante.com", "1234", "Recepcionista", "Completo"));
        TRABAJADORES.add(new Trabajador(2, "70033344", "Carla", "Reyes",
                "988333444", "carla@restaurante.com", "abcd", "Mesero", "Mañana"));
    }

    public static Trabajador buscarPorCorreo(String correo) {
        for (Trabajador t : TRABAJADORES) {
            if (t.getCorreo().equalsIgnoreCase(correo)) return t;
        }
        return null;
    }
}