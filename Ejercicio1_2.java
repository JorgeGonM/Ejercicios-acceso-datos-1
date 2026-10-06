import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio1_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce la ruta del fichero: ");
        String ruta = scanner.nextLine();

        System.out.print("Introduce el carácter a buscar: ");
        String entrada = scanner.nextLine();

        if (entrada.isEmpty()) {
            System.out.println("Error: No has introducido ningún carácter.");
            return;
        }

        char caracterBuscado = entrada.charAt(0);
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            int codigo;
            // Lee carácter a carácter (devuelve -1 al llegar al final del fichero)
            while ((codigo = br.read()) != -1) {
                char caracterActual = (char) codigo;
                if (caracterActual == caracterBuscado) {
                    contador++;
                }
            }

            System.out.println("El carácter '" + caracterBuscado + "' aparece " + contador + " veces.");

        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }

        scanner.close();
    }
}
