/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Diego
 */
public class Ubicacion {
   
   private int idUbicacion;
    private String nombreUbicacion;
    private boolean aceptaMascotas;

    public Ubicacion(
            int idUbicacion,
            String nombreUbicacion,
            boolean aceptaMascotas) {

        this.idUbicacion = idUbicacion;
        this.nombreUbicacion = nombreUbicacion;
        this.aceptaMascotas = aceptaMascotas;
    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public String getNombreUbicacion() {
        return nombreUbicacion;
    }

    public boolean isAceptaMascotas() {
        return aceptaMascotas;
    } 
    
}
