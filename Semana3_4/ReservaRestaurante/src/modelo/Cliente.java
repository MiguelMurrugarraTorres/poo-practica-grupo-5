/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public class Cliente extends Persona {

    private int idSocio;

    public Cliente(int idSocio, String dni, String nombres, String apellidos,
                   String telefono, String correo) {
        super(dni, nombres, apellidos, telefono, correo);
        this.idSocio = idSocio;
    }

    public int getIdSocio() { return idSocio; }
    public void setIdSocio(int idSocio) { this.idSocio = idSocio; }   
}