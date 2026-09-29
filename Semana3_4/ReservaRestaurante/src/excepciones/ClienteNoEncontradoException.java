/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author Diego
 */
public class ClienteNoEncontradoException extends NegocioException {
    public ClienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}