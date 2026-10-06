import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Juego implements Serializable {
    // Es recomendable incluir un serialVersionUID para la serialización
    private static final long serialVersionUID = 1L;

    private String secretWord;
    private Set<Character> guessedLetters; // Letras que el usuario ya ha intentado
    private int errors;
    private final int maxErrors;
    private boolean isFinished;

    public Juego(String secretWord, int maxErrors) {
        this.secretWord = secretWord.toLowerCase();
        this.maxErrors = maxErrors;
        this.errors = 0;
        this.guessedLetters = new HashSet<>();
        this.isFinished = false;
    }

    // Procesa un intento de letra por parte del jugador
    public boolean playLetter(char letter) {
        letter = Character.toLowerCase(letter);

        // Si la letra ya se había intentado, no la penalizamos
        if (guessedLetters.contains(letter)) {
            System.out.println("Ya has probado la letra '" + letter + "'.");
            return false;
        }

        guessedLetters.add(letter);

        // Si la palabra no contiene la letra, incrementamos los errores
        if (!secretWord.contains(String.valueOf(letter))) {
            errors++;
            System.out.println("¡Incorrecto! La letra '" + letter + "' no está en la palabra.");
            return false;
        } else {
            System.out.println("¡Acierto! La letra '" + letter + "' está en la palabra.");
            return true;
        }
    }

    // Devuelve la palabra oculta mostrando solo las letras adivinadas (ej: "a _ o r c a d o")
    public String getMaskedWord() {
        StringBuilder masked = new StringBuilder();
        for (char c : secretWord.toCharArray()) {
            if (guessedLetters.contains(c) || !Character.isLetter(c)) {
                masked.append(c).append(" ");
            } else {
                masked.append("_ ");
            }
        }
        return masked.toString().trim();
    }

    // Comprueba si el jugador ha adivinado la palabra completa
    public boolean isWordGuessed() {
        for (char c : secretWord.toCharArray()) {
            if (Character.isLetter(c) && !guessedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }

    // Getters y estado del juego
    public int getErrors() {
        return errors;
    }

    public int getMaxErrors() {
        return maxErrors;
    }

    public boolean isFinished() {
        return isFinished || isWordGuessed() || errors >= maxErrors;
    }

    public String getSecretWord() {
        return secretWord;
    }
}
