
 
package servicio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import excepciones.CredencialesInvalidasException;
import modelo.Trabajador;
import repositorio.AsistenciaRepositorio;
import repositorio.AsistenciaRepositorio.AsistenciaHoy;
import repositorio.TrabajadorRepositorio;

public class TrabajadorServicio {

    public Trabajador iniciarSesion(String correo, String contrasena) throws CredencialesInvalidasException {
        if (correo == null || correo.isBlank() || contrasena == null || contrasena.isBlank()) {
            throw new CredencialesInvalidasException("Completa el correo y la contraseña.");
        }
        Trabajador t = TrabajadorRepositorio.buscarPorCorreo(correo.trim());
        if (t == null || !t.getContrasena().equals(contrasena)) {
            throw new CredencialesInvalidasException("Correo o contraseña incorrectos.");
        }

        AsistenciaRepositorio.registrarEntradaSiNoExiste(t.getIdTrabajador());
        AsistenciaHoy hoy = AsistenciaRepositorio.consultarHoy(t.getIdTrabajador());

        t.setHoraEntrada(LocalDateTime.of(LocalDate.now(), hoy.horaEntrada));
        t.setHoraSalida(hoy.horaSalida == null ? null : LocalDateTime.of(LocalDate.now(), hoy.horaSalida));

        return t;
    }

    public void registrarSalida(Trabajador t) {
        AsistenciaRepositorio.registrarSalida(t.getIdTrabajador());
        t.setHoraSalida(LocalDateTime.now());
    }
}