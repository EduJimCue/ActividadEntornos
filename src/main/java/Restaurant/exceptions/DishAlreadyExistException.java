package Restaurant.exceptions;

public class DishAlreadyExistException extends Exception {

    public DishAlreadyExistException(String message) {
        super(message);
    }

    public DishAlreadyExistException() {
        super("El plato ya existe");
    }
}
