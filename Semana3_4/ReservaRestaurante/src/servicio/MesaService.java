/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package servicio;
import java.util.ArrayList;
import modelo.mesa;
import modelo.Ubicacion;
import java.util.List;

/**
 *
 * @author Diego
 */
public class MesaService {
private List<mesa> mesas;

    public MesaService() {
        mesas = new ArrayList<>();
    }

    public void registrarMesa(mesa mesa) {

        mesas.add(mesa);

        System.out.println(
                "Mesa "
                + mesa.getNumeroMesa()
                + " registrada correctamente."
        );
    }

    public mesa buscarMesa(int numeroMesa) {

        for (mesa mesa : mesas) {

            if (mesa.getNumeroMesa() == numeroMesa) {
                return mesa;
            }
        }

        return null;
    }

    public List<mesa> listarMesas() {
        return mesas;
    }

    public List<mesa> listarMesasDisponibles() {

        List<mesa> disponibles =
                new ArrayList<>();

        for (mesa mesa : mesas) {

            if (mesa.estaDisponible()) {
                disponibles.add(mesa);
            }
        }

        return disponibles;
    }
    
}
