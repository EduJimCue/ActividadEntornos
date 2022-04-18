package Restaurant.exceptions;

public class OrderNotFoundException extends Exception {

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException() {
        super("El pedido no existe");
    }
}
