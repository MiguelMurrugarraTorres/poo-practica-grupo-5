package modelo;

import java.time.LocalTime;
import modelo.Persona;

public abstract class Trabajador extends Persona {

    private int idTrabajador;
    private String contrasena;
    private LocalTime horarioEntrada;
    private LocalTime horarioSalida;

    public Trabajador(
            int dni,
            String nombres,
            String apellidos,
            String telefono,
            String correo,
            int idTrabajador,
            String contrasena,
            LocalTime horarioEntrada,
            LocalTime horarioSalida) {

        super(
                dni,
                nombres,
                apellidos,
                telefono,
                correo
        );

        this.idTrabajador = idTrabajador;
        this.contrasena = contrasena;
        this.horarioEntrada = horarioEntrada;
        this.horarioSalida = horarioSalida;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public LocalTime getHorarioEntrada() {
        return horarioEntrada;
    }

    public LocalTime getHorarioSalida() {
        return horarioSalida;
    }

    public boolean iniciarSesion(String contrasenaIngresada) {

        return contrasena.equals(contrasenaIngresada);
    }

    public abstract String rol();
}