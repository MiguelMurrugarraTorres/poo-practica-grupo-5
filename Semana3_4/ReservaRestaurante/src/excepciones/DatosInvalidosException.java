/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author Diego
 */
public class DatosInvalidosException extends NegocioException {
    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }
}