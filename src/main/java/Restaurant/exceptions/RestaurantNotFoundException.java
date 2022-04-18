package Restaurant.exceptions;

public class RestaurantNotFoundException extends Exception {

    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException() {
        super("El restaurante no existe");
    }
}
