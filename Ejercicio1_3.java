import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ejercicio1_3 {
    public static void main(String[] args) {

        // Validamos los argumentos e imprimimos como se deben de introducir.
        if (args.length < 2) {
            System.out.println("Error: Debes proporcionar la ruta del fichero y el tipo de ordenación.");
            System.out.println("Modos posibles:");
            System.out.println("  asc_sens   : Ascendente (sensible a mayúsculas)");
            System.out.println("  asc_insens : Ascendente (no sensible a mayúsculas)");
            System.out.println("  desc_sens  : Descendente (sensible a mayúsculas)");
            System.out.println("  desc_insens: Descendente (no sensible a mayúsculas)");
            return;
        }

        String rutaFichero = args[0];
        String modo = args[1].toLowerCase();

        // Declaramos el comparador y el sufijo
        Comparator<String> comparador;
        String sufijo;

        switch (modo) {
            case "asc_sens":
                comparador = Comparator.naturalOrder();
                sufijo = "_asc_case";
                break;
            case "asc_insens":
                comparador = String.CASE_INSENSITIVE_ORDER;
                sufijo = "_asc_non_case";
                break;
            case "desc_sens":
                comparador = Comparator.reverseOrder();
                sufijo = "_desc_case";
                break;
            case "desc_insens":
                comparador = Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER);
                sufijo = "_desc_non_case";
                break;
            default:
                System.out.println("Error: Modo de ordenación no válido.");
                return;
        }

        //Creamos una lista para almacenar las líneas del fichero
        List<String> lineas = new ArrayList<>();

        //Leemos todas las líneas del fichero
        try (BufferedReader br = new BufferedReader(new FileReader(rutaFichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero de entrada: " + e.getMessage());
            return;
        }

        //Ordenamos las líneas según el comparador seleccionado
        lineas.sort(comparador);

        //Generamos el nombre del fichero de salida
        String rutaSalida = generarNombreSalida(rutaFichero, sufijo);

        //Escribimos las líneas ordenadas en el nuevo fichero
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaSalida))) {
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
            System.out.println("Fichero ordenado guardado en: " + rutaSalida);
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero de salida: " + e.getMessage());
        }
    }

    // Método auxiliar para añadir el sufijo manteniendo la extensión original
    private static String generarNombreSalida(String rutaOriginal, String sufijo) {
        int puntoPos = rutaOriginal.lastIndexOf('.');
        
        if (puntoPos != -1) {
            String nombreSinExt = rutaOriginal.substring(0, puntoPos);
            String extension = rutaOriginal.substring(puntoPos);
            return nombreSinExt + sufijo + extension;
        } else {
            return rutaOriginal + sufijo + ".txt";
        }
    }
}
