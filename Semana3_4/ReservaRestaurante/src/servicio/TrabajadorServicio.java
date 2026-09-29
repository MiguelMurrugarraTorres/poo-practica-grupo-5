/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;
import java.time.LocalDateTime;
import excepciones.CredencialesInvalidasException;
import modelo.Trabajador;
import repositorio.TrabajadorRepositorio;
/**
 *
 * @author Diego
 */
public class TrabajadorServicio {
 public Trabajador iniciarSesion(String correo, String contrasena) throws CredencialesInvalidasException {
        if (correo == null || correo.isBlank() || contrasena == null || contrasena.isBlank()) {
            throw new CredencialesInvalidasException("Completa el correo y la contraseña.");
        }
        Trabajador t = TrabajadorRepositorio.buscarPorCorreo(correo.trim());
        if (t == null || !t.getContrasena().equals(contrasena)) {
            throw new CredencialesInvalidasException("Correo o contraseña incorrectos.");
        }
        t.setHoraEntrada(LocalDateTime.now());
        t.setHoraSalida(null);
        return t;
    }

    public void registrarSalida(Trabajador t) {
        t.setHoraSalida(LocalDateTime.now());
    }
}