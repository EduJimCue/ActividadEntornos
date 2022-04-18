package Restaurant.exceptions;

public class UserAlreadyExistException extends Exception {

    public UserAlreadyExistException(String message) {
        super(message);
    }

    public UserAlreadyExistException() {super("Ese username ya existe en la base de datos elige otro username");
    }
}
