package modelo;

import java.util.ArrayList;
import java.util.List;

public class Estudiante {
    
    private String codigo;
    private String nombre;
    private String apellido;
    private List<Calificacion> calificaciones;

    public Estudiante(String codigo, String nombre, String apellido) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.calificaciones = new ArrayList<>(); // Inicializa la lista vacía
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public List<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public void agregarCalificacion(Calificacion calificacion) {
        this.calificaciones.add(calificacion);
    }

    public boolean tieneCalificaciones() {
        return !this.calificaciones.isEmpty();
    }

    public double calcularPromedio() {
        if (!tieneCalificaciones()) {
            return 0.0;
        }
        
        double suma = 0.0;
        for (Calificacion c : calificaciones) {
            suma += c.getNota();
        }
        
        return suma / calificaciones.size();
    }

    @Override
    public String toString() {
        return "[" + codigo + "] " + nombre + " " + apellido + " | Calificaciones registradas: " + calificaciones.size();
    }
}