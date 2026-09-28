/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

public class Sesion {

    private static int idTrabajador = 1;

    public static int getIdTrabajador() { return idTrabajador; }

    public static void iniciar(int id) { idTrabajador = id; }
}