import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Ejercicio1_5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce la ruta del fichero de entrada (ej. coches.txt): ");
        String rutaEntrada = scanner.nextLine();
        String rutaSalida = "marcas.txt";

        // Usamos TreeMap para que las marcas estén ordenadas alfabéticamente.
        // Cada marca tendrá un TreeSet para que sus modelos también estén ordenados y evitar duplicados.
        Map<String, Set<String>> agrupacion = new TreeMap<>();

        // 1. Leer el fichero de entrada y procesar cada línea
        try (BufferedReader br = new BufferedReader(new FileReader(rutaEntrada))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                // Separar la marca del modelo usando el primer espacio como delimitador
                int primerEspacio = linea.indexOf(' ');
                
                if (primerEspacio != -1) {
                    String marca = linea.substring(0, primerEspacio).trim();
                    String modelo = linea.substring(primerEspacio + 1).trim();

                    // Si la marca no existe en el mapa, la inicializamos con un TreeSet
                    agrupacion.putIfAbsent(marca, new TreeSet<>());
                    // Añadimos el modelo al conjunto de esa marca
                    agrupacion.get(marca).add(modelo);
                } else {
                    // Si la línea consta de una sola palabra, la tomamos como marca sin modelo
                    agrupacion.putIfAbsent(linea, new TreeSet<>());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero de entrada: " + e.getMessage());
            return;
        }

        // 2. Escribir el resultado formateado en marcas.txt
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaSalida))) {
            for (Map.Entry<String, Set<String>> entry : agrupacion.entrySet()) {
                String marca = entry.getKey();
                Set<String> modelos = entry.getValue();

                // Unimos los modelos separados por coma y espacio (ej. "C1, C3 Aircross")
                String listaModelos = String.join(", ", modelos);

                // Formato de salida: Marca: Modelo1, Modelo2
                String lineaSalida = marca + ": " + listaModelos;
                
                bw.write(lineaSalida);
                bw.newLine();
            }

            System.out.println("Fichero generado con éxito: " + rutaSalida);

        } catch (IOException e) {
            System.out.println("Error al escribir el fichero de salida: " + e.getMessage());
        }

        scanner.close();
    }
}
