import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Ejercicio1_4 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce la ruta del fichero del test: ");
        String ruta = scanner.nextLine();

        // Lista para almacenar los resultados de cada alumno
        List<ResultadoAlumno> alumnos = new ArrayList<>();

        // Contadores para el resumen de calificaciones, usando LinkedHashMap para mantener el orden de inserción 
        Map<String, Integer> contadores = new LinkedHashMap<>();
        contadores.put("Excelente", 0);
        contadores.put("Notable", 0);
        contadores.put("Bien", 0);
        contadores.put("Aprobado", 0);
        contadores.put("Suspenso", 0);

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {

            // La primera línea contiene las respuestas correctas del test
            String respuestasCorrectas = br.readLine();

            if (respuestasCorrectas == null || respuestasCorrectas.trim().isEmpty()) {
                System.out.println("Error: El fichero está vacío o no contiene la plantilla de respuestas.");
                return;
            }

            respuestasCorrectas = respuestasCorrectas.trim();
            String lineaAlumno;

            // Procesar cada alumno, las siguientes lineas
            while ((lineaAlumno = br.readLine()) != null) {
                lineaAlumno = lineaAlumno.trim();
                if (lineaAlumno.isEmpty()) continue;

                // Separar el código del alumno de sus respuestas
                // (usamos split de espacios para admitir tabuladores o varios espacios)
                String[] partes = lineaAlumno.split("\\s+", 2);
                if (partes.length < 2) {
                    System.out.println("Línea ignorada por formato incorrecto: " + lineaAlumno);
                    continue;
                }

                String idAlumno = partes[0];
                String respuestasAlumno = partes[1];

                // Calcular la nota
                double nota = calcularNota(respuestasCorrectas, respuestasAlumno);

                // Determinar la calificación según la escala
                String calificacion = obtenerCalificacion(nota);

                // Guardar el resultado del alumno
                alumnos.add(new ResultadoAlumno(idAlumno, nota, calificacion));

                // Incrementar contador de la categoría
                contadores.put(calificacion, contadores.get(calificacion) + 1);
            }

        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
            return;
        }

        //Mostramos los resultados individuales de cada alumno
        //Formato de salida hecho para que quede alineado y sea fácil de leer
        System.out.println("\n--- RESULTADOS DEL CUESTIONARIO ---");
        System.out.printf("%-12s %-8s %-12s\n", "ID ALUMNO", "NOTA", "CALIFICACIÓN");
        System.out.println("------------------------------------");
        for (ResultadoAlumno a : alumnos) {
            System.out.printf("%-12s %-8.2f %-12s\n", a.getId(), a.getNota(), a.getCalificacion());
        }

        //Mostramos la tabla resumen con porcentajes
        int totalAlumnos = alumnos.size();
        System.out.println("\n====================================");
        System.out.println("          TABLA RESUMEN             ");
        System.out.println("====================================");
        System.out.printf("%-12s %-10s %-12s\n", "CALIFICACIÓN", "ALUMNOS", "PORCENTAJE");
        System.out.println("------------------------------------");

        if (totalAlumnos > 0) {

            // Calculamos y mostramos el porcentaje de cada calificación
            for (Map.Entry<String, Integer> entry : contadores.entrySet()) {
                String calif = entry.getKey();
                int cantidad = entry.getValue();
                double porcentaje = ((double) cantidad / totalAlumnos) * 100;
                System.out.printf("%-12s %-10d %6.2f%%\n", calif, cantidad, porcentaje);
            }
            System.out.println("------------------------------------");
            System.out.printf("%-12s %-10d %6.2f%%\n", "TOTAL", totalAlumnos, 100.0);
        } else {
            System.out.println("No se procesaron alumnos.");
        }

        scanner.close();
    }

    // Puntuación: Acierto +0.5 | Fallo -0.15 | Blanco 0.0 (mínimo 0 puntos)
    private static double calcularNota(String solucion, String respuestas) {
        double nota = 0.0;

        int minLongitud = Math.min(solucion.length(), respuestas.length());

        for (int i = 0; i < minLongitud; i++) {
            char rCorrecta = solucion.charAt(i);
            char rAlumno = respuestas.charAt(i);

            if (rAlumno == ' ') {
                // Respuesta en blanco: no suma ni resta
                continue;
            } else if (rAlumno == rCorrecta) {
                nota += 0.5;
            } else {
                nota -= 0.15;
            }
        }

        // Si la nota final fuera negativa, se trunca a 0
        return Math.max(0.0, nota);
    }

    // Escala de calificaciones indicada en el enunciado
    private static String obtenerCalificacion(double nota) {
        if (nota >= 8.5) {
            return "Excelente";
        } else if (nota >= 7.0) {
            return "Notable";
        } else if (nota >= 6.0) {
            return "Bien";
        } else if (nota >= 5.0) {
            return "Aprobado";
        } else {
            return "Suspenso";
        }
    }
}
