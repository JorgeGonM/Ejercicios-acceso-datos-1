import java.io.File;
import java.util.Scanner;

public class Ejercicio1_1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Introduce la ruta del directorio: ");
        String ruta = scanner.nextLine();

        File directorio = new File(ruta);

        // Verificamos que la ruta existe y sea realmente un directorio
        if (!directorio.exists()) {
            System.out.println("Error: La ruta especificada no existe.");
            return;
        }

        if (!directorio.isDirectory()) {
            System.out.println("Error: La ruta especificada no es un directorio.");
            return;
        }

        // Obtenemos la lista de ficheros y subdirectorios
        File[] contenido = directorio.listFiles();

        if (contenido == null) {
            System.out.println("No se tienen permisos para acceder al contenido del directorio.");
            return;
        }

        System.out.println("\nContenido de: " + directorio.getAbsolutePath() + "\n");

        for (File elemento : contenido) {
            StringBuilder permisos = new StringBuilder();

            //Indicar si es directorio ('d') o fichero ('-')
            if (elemento.isDirectory()) {
                permisos.append("d");
            } else {
                permisos.append("-");
            }

            //Permiso de lectura ('r' o '-')
            if (elemento.canRead()) {
                permisos.append("r");
            } else {
                permisos.append("-");
            }

            //Permiso de escritura ('w' o '-')
            if (elemento.canWrite()) {
                permisos.append("w");
            } else {
                permisos.append("-");
            }

            //Permiso de ejecución ('x' o '-')
            if (elemento.canExecute()) {
                permisos.append("x");
            } else {
                permisos.append("-");
            }

            //Mostramos el resultado en pantalla
            System.out.println(permisos.toString() + " " + elemento.getName());
        }

        scanner.close();
    }
}
