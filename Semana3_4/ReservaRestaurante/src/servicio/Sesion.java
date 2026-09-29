/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;
import modelo.Trabajador;
public class Sesion {


    private static Trabajador trabajador;

    public static void iniciar(Trabajador t) { trabajador = t; }

    public static Trabajador getTrabajador() { return trabajador; }

    public static int getIdTrabajador() {
        return trabajador != null ? trabajador.getIdTrabajador() : 0;
    }

    public static void cerrar() { trabajador = null; }
}