package modelo;

public abstract class Persona {

    private int dni;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;

    public Persona(
            int dni,
            String nombres,
            String apellidos,
            String telefono,
            String correo) {

        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
    }

    public int getDni() {
        return dni;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}