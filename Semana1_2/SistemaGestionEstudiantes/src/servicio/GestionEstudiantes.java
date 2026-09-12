package servicio;

import java.util.ArrayList;
import java.util.List;
import modelo.Estudiante;
import modelo.Calificacion;
import excepciones.CodigoDuplicadoException;
import excepciones.EstudianteNoEncontradoException;
import excepciones.CalificacionInvalidaException;

public class GestionEstudiantes {

    private List<Estudiante> estudiantes;

    public GestionEstudiantes() {
        this.estudiantes = new ArrayList<>();
    }

    // 1. Registrar estudiante
    public void registrarEstudiante(Estudiante nuevoEstudiante) throws CodigoDuplicadoException {
        for (Estudiante e : estudiantes) {
            if (e.getCodigo().equalsIgnoreCase(nuevoEstudiante.getCodigo())) {
                throw new CodigoDuplicadoException("El código " + nuevoEstudiante.getCodigo() + " ya está registrado.");
            }
        }
        estudiantes.add(nuevoEstudiante);
    }

    // 2. Listar estudiantes
    public List<Estudiante> listarEstudiantes() {
        return estudiantes;
    }

    // 3. Buscar estudiante por código
    public Estudiante buscarEstudiantePorCodigo(String codigo) throws EstudianteNoEncontradoException {
        for (Estudiante e : estudiantes) {
            if (e.getCodigo().equalsIgnoreCase(codigo)) {
                return e;
            }
        }
        throw new EstudianteNoEncontradoException("No existe ningún estudiante con el código: " + codigo);
    }

    // 4. Registrar calificación
    public void registrarCalificacion(String codigo, Calificacion calificacion) 
            throws EstudianteNoEncontradoException, CalificacionInvalidaException {
        
        // Validación del rango de nota (0.0 a 20.0)
        if (calificacion.getNota() < 0.0 || calificacion.getNota() > 20.0) {
            throw new CalificacionInvalidaException("La nota debe estar entre 0.0 y 20.0. Valor ingresado: " + calificacion.getNota());
        }

        // Reutiliza la búsqueda: si no existe, lanzará EstudianteNoEncontradoException
        Estudiante estudiante = buscarEstudiantePorCodigo(codigo);
        estudiante.agregarCalificacion(calificacion);
    }

    // 5. Calcular promedio
    public double calcularPromedio(String codigo) throws EstudianteNoEncontradoException {
        Estudiante estudiante = buscarEstudiantePorCodigo(codigo);
        return estudiante.calcularPromedio();
    }
}