/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;
import java.util.ArrayList;
import java.util.List;
import modelo.Mesa;
import modelo.Ubicacion;

public class MesaRepositorio {

    private static final List<Ubicacion> UBICACIONES = new ArrayList<>();
    private static final List<Mesa> MESAS = new ArrayList<>();

    static {
        UBICACIONES.add(new Ubicacion(1, "Sala", false));
        UBICACIONES.add(new Ubicacion(2, "Terraza", true));
        UBICACIONES.add(new Ubicacion(3, "Campo", true));
        UBICACIONES.add(new Ubicacion(4, "Afuera", true));

        // {numero, capacidad, ubicación (1-4), activa (1 = sí, 0 = no)}
        int[][] datos = {
            {1, 4, 1, 1}, {2, 2, 1, 1}, {3, 6, 1, 1}, {4, 4, 1, 1}, {5, 4, 1, 1},
            {6, 4, 1, 0}, {7, 4, 1, 1}, {8, 8, 1, 1}, {9, 4, 1, 1}, {10, 4, 1, 1},
            {11, 6, 1, 1},
            {12, 4, 2, 1}, {13, 4, 2, 1}, {14, 2, 2, 1}, {15, 6, 2, 0},
            {16, 8, 3, 1}, {17, 4, 3, 1}, {18, 4, 3, 1},
            {19, 2, 4, 1}, {20, 4, 4, 1}
        };
        for (int[] d : datos) {
            MESAS.add(new Mesa(d[0], d[0], d[1], d[3] == 1, UBICACIONES.get(d[2] - 1)));
        }
    }

    public static List<Ubicacion> listarUbicaciones() { return new ArrayList<>(UBICACIONES); }
    public static List<Mesa> listarMesas() { return new ArrayList<>(MESAS); }

    public static Ubicacion buscarUbicacion(String nombre) {
        for (Ubicacion u : UBICACIONES) {
            if (u.getNombreUbicacion().equalsIgnoreCase(nombre)) return u;
        }
        return null;
    }

    public static Mesa buscarPorNumero(int numero) {
        for (Mesa m : MESAS) {
            if (m.getNumeroMesa() == numero) return m;
        }
        return null;
    }

    public static int siguienteId() {
        int max = 0;
        for (Mesa m : MESAS) max = Math.max(max, m.getIdMesa());
        return max + 1;
    }

    public static void guardar(Mesa mesa) { MESAS.add(mesa); }

    public static void eliminar(Mesa mesa) { MESAS.remove(mesa); }
}