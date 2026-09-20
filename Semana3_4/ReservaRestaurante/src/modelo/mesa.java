/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package modelo;

/**
 *
 * @author Diego
 */
public class mesa {

   private int idMesa;
    private int numeroMesa;
    private int capacidad;
    private boolean estado;
    private Ubicacion ubicacion;

    public mesa(
            int idMesa,
            int numeroMesa,
            int capacidad,
            Ubicacion ubicacion) {

        this.idMesa = idMesa;
        this.numeroMesa = numeroMesa;
        this.capacidad = capacidad;
        this.estado = true;
        this.ubicacion = ubicacion;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void cambiarEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean estaDisponible() {
        return estado;
    }
   
}

