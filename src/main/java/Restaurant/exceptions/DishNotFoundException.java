package Restaurant.exceptions;

public class DishNotFoundException extends Exception {

    public DishNotFoundException(String message) {
        super(message);
    }

    public DishNotFoundException() {
        super("El plato no existe");
    }
}
