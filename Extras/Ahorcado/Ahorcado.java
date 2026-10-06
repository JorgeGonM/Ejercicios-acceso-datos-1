import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Ahorcado {

    private static final String[] PALABRAS_DISPONIBLES = {
        "programacion", "desarrollo", "fichero", "java", "serializacion", "ahorcado", "objeto"
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Juego juego = null;

        System.out.println("=== JUEGO DEL AHORCADO ===");
        System.out.print("¿Deseas cargar una partida guardada? (s/n): ");
        String respuestaCargar = scanner.nextLine().trim().toLowerCase();

        if (respuestaCargar.equals("s")) {
            System.out.print("Introduce el nombre o ruta del archivo guardado: ");
            String nombreArchivo = scanner.nextLine().trim();
            juego = cargarPartida(nombreArchivo);
        }

        // Si no se cargó una partida o la carga falló, inicializamos un juego nuevo
        if (juego == null) {
            System.out.println("\nIniciando una nueva partida...");
            String palabraAleatoria = PALABRAS_DISPONIBLES[(int) (Math.random() * PALABRAS_DISPONIBLES.length)];
            juego = new Juego(palabraAleatoria, 6); // 6 fallos máximos
        }

        // Bucle del juego
        while (!juego.isFinished()) {
            System.out.println("\n------------------------------------");
            System.out.println("Palabra: " + juego.getMaskedWord());
            System.out.println("Fallos: " + juego.getErrors() + " / " + juego.getMaxErrors());
            System.out.print("Introduce una letra (o escribe 'SALIR' para guardar y abandonar): ");

            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                continue;
            }

            // Opción de salir y guardar la partida
            if (entrada.equalsIgnoreCase("SALIR")) {
                System.out.print("¿Quieres guardar la partida antes de salir? (s/n): ");
                String respuestaGuardar = scanner.nextLine().trim().toLowerCase();

                if (respuestaGuardar.equals("s")) {
                    System.out.print("Introduce el nombre con el que deseas guardar el archivo (ej: partida.dat): ");
                    String nombreArchivo = scanner.nextLine().trim();
                    guardarPartida(juego, nombreArchivo);
                }
                System.out.println("¡Gracias por jugar! Hasta pronto.");
                scanner.close();
                return;
            }

            // Jugar la letra introducida
            char letra = entrada.charAt(0);
            juego.playLetter(letra);
        }

        // Fin de la partida (victoria o derrota)
        System.out.println("\n====================================");
        if (juego.isWordGuessed()) {
            System.out.println("¡FELICIDADES! Has adivinado la palabra: " + juego.getSecretWord());
        } else {
            System.out.println("¡Juego OVER! Has agotado todos tus intentos.");
            System.out.println("La palabra secreta era: " + juego.getSecretWord());
        }
        System.out.println("====================================");

        scanner.close();
    }

    // Métodos de serialización (Guardar)
    private static void guardarPartida(Juego juego, String rutaArchivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(juego);
            System.out.println("Partida guardada con éxito en: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar la partida: " + e.getMessage());
        }
    }

    // Métodos de deserialización (Cargar)
    private static Juego cargarPartida(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            System.out.println("Error: El archivo especificado no existe.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Juego juegoCargado = (Juego) ois.readObject();
            System.out.println("Partida cargada correctamente.");
            return juegoCargado;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar la partida: " + e.getMessage());
            return null;
        }
    }
}