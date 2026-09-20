/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import modelo.Trabajador;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private List<Trabajador> trabajadores;

    public UsuarioService() {
        trabajadores = new ArrayList<>();
    }

    public void registrarUsuario(Trabajador trabajador) {

        trabajadores.add(trabajador);

        System.out.println(
                "Usuario registrado: "
                + trabajador.getNombreCompleto()
        );
    }

    // Sobrecarga 1
    public Trabajador buscarUsuario(int idTrabajador) {

        for (Trabajador trabajador : trabajadores) {

            if (trabajador.getIdTrabajador()
                    == idTrabajador) {

                return trabajador;
            }
        }

        return null;
    }

    // Sobrecarga 2
    public Trabajador buscarUsuario(String nombre) {

        for (Trabajador trabajador : trabajadores) {

            if (trabajador.getNombres()
                    .equalsIgnoreCase(nombre)) {

                return trabajador;
            }
        }

        return null;
    }

    public List<Trabajador> listarUsuarios() {
        return trabajadores;
    }
}