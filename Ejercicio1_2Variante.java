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

        //Creamos un mapa para almacenar la frecuencia de cada carácter
        Map<Character, Integer> frecuencias = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            int codigo;
            while ((codigo = br.read()) != -1) {
                char c = (char) codigo;
                
                //Ignoramos los saltos de línea para contar solo los caracteres visibles
                if (c == '\r' || c == '\n') {
                    continue;
                }
                //Actualizamos la frecuencia del carácter en el mapa, si ya existe, incrementamos su contador; si no, lo inicializamos a 1.
                frecuencias.put(c, frecuencias.getOrDefault(c, 0) + 1);
            }

            if (frecuencias.isEmpty()) {
                System.out.println("El fichero está vacío o no contiene caracteres válidos.");
                return;
            }

            //Buscamos el carácter con el número máximo de ocurrencias
            char masUsado = ' ';
            int maxFrecuencia = -1;
            
            //Iteramos sobre las entradas del mapa para encontrar el carácter más usado y su frecuencia
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
