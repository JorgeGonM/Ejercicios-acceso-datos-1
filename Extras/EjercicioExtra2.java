import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class EjercicioExtra2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Inicializar el directorio de trabajo actual con la propiedad del sistema
        Path directorioActual = Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize();

        System.out.println("¡Bienvenido a la MiniShell! Escribe 'exit' o 'quit' para salir.\n");

        while (true) {
            // Mostrar el prompt con la ruta actual
            System.out.print(directorioActual + "> ");
            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                continue;
            }

            // Salir del programa si se ingresa 'exit' o 'quit'
            if (entrada.equalsIgnoreCase("exit") || entrada.equalsIgnoreCase("quit")) {
                System.out.println("Saliendo de la MiniShell...");
                break;
            }

            // Separar el comando de los argumentos (se divide por el primer espacio)
            String[] partes = entrada.split("\\s+", 2);
            String comando = partes[0].toLowerCase();
            String argumento = partes.length > 1 ? partes[1] : "";

            switch (comando) {
                case "cd":
                    directorioActual = ejecutarCd(directorioActual, argumento);
                    break;
                case "ls":
                    ejecutarLs(directorioActual);
                    break;
                case "cat":
                    ejecutarCat(directorioActual, argumento);
                    break;
                default:
                    System.out.println("Comando no reconocido. Comandos disponibles: cd, ls, cat, exit");
                    break;
            }
        }

        scanner.close();
    }

    // Método para cambiar de directorio (soporta rutas absolutas y relativas)
    private static Path ejecutarCd(Path directorioActual, String argumento) {
        if (argumento.isEmpty()) {
            System.out.println("Uso: cd <directorio>");
            return directorioActual;
        }

        Path nuevaRuta;
        Path argumentoPath = Paths.get(argumento);

        // Si la ruta introducida es absoluta, la tomamos tal cual; si no, la resolvemos desde el directorio actual
        if (argumentoPath.isAbsolute()) {
            nuevaRuta = argumentoPath.normalize();
        } else {
            nuevaRuta = directorioActual.resolve(argumentoPath).normalize();
        }

        File nuevoDir = nuevaRuta.toFile();

        if (!nuevoDir.exists()) {
            System.out.println("cd: El directorio no existe: " + argumento);
            return directorioActual;
        }

        if (!nuevoDir.isDirectory()) {
            System.out.println("cd: No es un directorio: " + argumento);
            return directorioActual;
        }

        if (!nuevoDir.canRead()) {
            System.out.println("cd: Permiso denegado para acceder al directorio.");
            return directorioActual;
        }

        return nuevaRuta;
    }

    // Método para listar el contenido del directorio actual
    private static void ejecutarLs(Path directorioActual) {
        File dir = directorioActual.toFile();
        File[] contenido = dir.listFiles();

        if (contenido == null) {
            System.out.println("ls: No se puede acceder al contenido del directorio.");
            return;
        }

        for (File elemento : contenido) {
            if (elemento.isDirectory()) {
                System.out.println(elemento.getName() + "/");
            } else {
                System.out.println(elemento.getName());
            }
        }
    }

    // Método para mostrar el contenido de un archivo en el directorio actual
    private static void ejecutarCat(Path directorioActual, String argumento) {
        if (argumento.isEmpty()) {
            System.out.println("Uso: cat <nombre_archivo>");
            return;
        }

        Path rutaArchivo = directorioActual.resolve(argumento).normalize();
        File archivo = rutaArchivo.toFile();

        if (!archivo.exists()) {
            System.out.println("cat: El archivo no existe: " + argumento);
            return;
        }

        if (archivo.isDirectory()) {
            System.out.println("cat: " + argumento + " es un directorio.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("cat: Error al leer el archivo: " + e.getMessage());
        }
    }
}
