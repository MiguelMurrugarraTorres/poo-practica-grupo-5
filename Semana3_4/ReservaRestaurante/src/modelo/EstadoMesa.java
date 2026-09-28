/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Diego
 */
public enum EstadoMesa {
    DISPONIBLE("Disponible"),
    RESERVADA("Reservada"),
    INACTIVA("Inactiva");

    private final String texto;

    EstadoMesa(String texto) { this.texto = texto; }

    public String getTexto() { return texto; }
}