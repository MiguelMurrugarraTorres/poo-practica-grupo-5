package modelo;

public class Calificacion {
    
    private String curso;
    private double nota;
    
    public Calificacion(String curso, double nota){
        this.curso = curso;
        this.nota = nota;
    }
    
    public String getCurso(){
     return curso;
    }
    
    public double getNota(){
        return nota;
    }
    
    @Override // remplazamos toString() que viene de Object.
    public String toString() {
        return curso + ": " + nota;
    }
    
    
}
