import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Ejercicio1_2Variante {

    //Encontramos el carácter más usado en un fichero de texto y cuántas veces aparece.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce la ruta del fichero: ");
        String ruta = scanner.nextLine();

        Map<Character, Integer> frecuencias = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            int codigo;
            while ((codigo = br.read()) != -1) {
                char c = (char) codigo;
                
                // Ignoramos saltos de línea si queremos contar solo texto visible
                if (c == '\r' || c == '\n') {
                    continue;
                }

                frecuencias.put(c, frecuencias.getOrDefault(c, 0) + 1);
            }

            if (frecuencias.isEmpty()) {
                System.out.println("El fichero está vacío o no contiene caracteres válidos.");
                return;
            }

            // Buscar el carácter con el número máximo de ocurrencias
            char masUsado = ' ';
            int maxFrecuencia = -1;

            for (Map.Entry<Character, Integer> entry : frecuencias.entrySet()) {
                if (entry.getValue() > maxFrecuencia) {
                    maxFrecuencia = entry.getValue();
                    masUsado = entry.getKey();
                }
            }

            System.out.println("El carácter más usado es '" + masUsado + "' con " + maxFrecuencia + " ocurrencias.");

        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }

        scanner.close();
    }
}
