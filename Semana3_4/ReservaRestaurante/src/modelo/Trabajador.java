/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.time.LocalDateTime;
/**
 *
 * @author Diego
 */
public class Trabajador extends Persona {

    private int idTrabajador;
    private String correo;
    private String contrasena;
    private String cargo;   // "Recepcionista", "Mesero", etc.
    private String turno;

    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;

    public Trabajador(int idTrabajador, String dni, String nombres, String apellidos,
                       String telefono, String correo, String contrasena,
                       String cargo, String turno) {
        super(dni, nombres, apellidos, telefono, correo);
        this.idTrabajador = idTrabajador;
        this.correo = correo;
        this.contrasena = contrasena;
        this.cargo = cargo;
        this.turno = turno;
    }

    public int getIdTrabajador() { return idTrabajador; }
    public String getCorreo() { return correo; }
    public String getContrasena() { return contrasena; }
    public String getCargo() { return cargo; }
    public String getTurno() { return turno; }

    public LocalDateTime getHoraEntrada() { return horaEntrada; }
    public void setHoraEntrada(LocalDateTime horaEntrada) { this.horaEntrada = horaEntrada; }

    public LocalDateTime getHoraSalida() { return horaSalida; }
    public void setHoraSalida(LocalDateTime horaSalida) { this.horaSalida = horaSalida; }

    public boolean tieneSalidaRegistrada() { return horaSalida != null; }
}