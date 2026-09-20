package modelo;

import java.time.LocalTime;

public class Mesero extends Trabajador {

    private String turno;

    public Mesero(
            int dni,
            String nombres,
            String apellidos,
            String telefono,
            String correo,
            int idTrabajador,
            String contrasena,
            LocalTime horarioEntrada,
            LocalTime horarioSalida,
            String turno) {

        super(
                dni,
                nombres,
                apellidos,
                telefono,
                correo,
                idTrabajador,
                contrasena,
                horarioEntrada,
                horarioSalida
        );

        this.turno = turno;
    }

    public String getTurno() {
        return turno;
    }

    @Override
    public String rol() {
        return "MESERO";
    }

    public boolean atiendeMesa() {
        return true;
    }
}