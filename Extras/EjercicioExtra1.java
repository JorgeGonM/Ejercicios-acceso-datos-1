import java.io.File;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class EjercicioExtra1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce la ruta del directorio inicial: ");
        String rutaInicial = scanner.nextLine();

        File directorioRaiz = new File(rutaInicial);

        // Validaciones iniciales
        if (!directorioRaiz.exists()) {
            System.out.println("Error: La ruta especificada no existe.");
            return;
        }

        if (!directorioRaiz.isDirectory()) {
            System.out.println("Error: La ruta especificada no es un directorio.");
            return;
        }

        // Crear la pila para gestionar los directorios pendientes
        Deque<Path> stack = new ArrayDeque<>();

        // Insertar el directorio inicial en la pila
        stack.push(directorioRaiz.toPath());

        // Bucle principal: mientras existan directorios pendientes en la pila
        while (!stack.isEmpty()) {
            // Extraer el directorio a procesar
            Path rutaActual = stack.pop();
            File directorioActual = rutaActual.toFile();

            System.out.println("\nDirectorio: " + directorioActual.getAbsolutePath());
            System.out.println("--------------------------------------------------");

            File[] contenido = directorioActual.listFiles();

            if (contenido == null) {
                System.out.println("  [Sin permisos para acceder a este directorio]");
                continue;
            }

            for (File elemento : contenido) {
                StringBuilder permisos = new StringBuilder();

                // 1. Indicar si es directorio ('d') o fichero ('-')
                if (elemento.isDirectory()) {
                    permisos.append("d");
                } else {
                    permisos.append("-");
                }

                // 2. Permisos de lectura, escritura y ejecución
                permisos.append(elemento.canRead() ? "r" : "-");
                permisos.append(elemento.canWrite() ? "w" : "-");
                permisos.append(elemento.canExecute() ? "x" : "-");

                // Imprimir resultado con sangría para mejor visualización
                System.out.println("  " + permisos.toString() + " " + elemento.getName());

                // Si es un directorio, lo añadimos a la pila para procesar sus subdirectorios
                if (elemento.isDirectory()) {
                    stack.push(elemento.toPath());
                }
            }
        }

        scanner.close();
    }
}
