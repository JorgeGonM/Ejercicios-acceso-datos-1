public class ResultadoAlumno {
    private String id;
    private double nota;
    private String calificacion;

    public ResultadoAlumno(String id, double nota, String calificacion) {
        this.id = id;
        this.nota = nota;
        this.calificacion = calificacion;
    }

    public String getId() {
        return id;
    }

    public double getNota() {
        return nota;
    }

    public String getCalificacion() {
        return calificacion;
    }
}