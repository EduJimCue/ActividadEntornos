package Restaurant;

import Restaurant.dao.*;
import Restaurant.domain.Dish;
import Restaurant.domain.Order;
import Restaurant.domain.Restaurant;
import Restaurant.domain.User;
import Restaurant.exceptions.*;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Menu {

    private Scanner keyboard;
    private Database database;
    private Connection connection;
    private User currentUser;

    public Menu() {
        keyboard = new Scanner(System.in);
    }

    public void connect() {
        database = new Database();
        connection = database.getConnection();
    }

    public void showMenu() throws SQLException, RestaurantNotFoundException {
        connect();
        login();

        String choice;
        do {
            System.out.println("Comida a domicilio (usuario actual: " + currentUser.getName() + ")");
            System.out.println("1. Añadir un Restaurante");
            System.out.println("2. Buscar un Restaurante");
            System.out.println("3. Eliminar un Restaurante");
            System.out.println("4. Modificar un Restaurante");
            System.out.println("5. Ver todo el catálogo de restaurantes");
            System.out.println("6. Añadir un plato");
            System.out.println("7. Buscar un plato");
            System.out.println("8. Eliminar un plato");
            System.out.println("9. Modificar un plato");
            System.out.println("10. Mostrar todos los platos");
            System.out.println("11. Añadir un usuario");
            System.out.println("12. Eliminar un usuario");
            System.out.println("13. Modificar un usuario");
            System.out.println("14. Ver todos los usuarios");
            System.out.println("15. Cambiar de usuario");
            System.out.println("16. Realizar un pedido");
            System.out.println("17. Consultar un pedido del usuario actual");
            System.out.println("18. Consultar los pedidos pendientes del usuario actual");
            System.out.println("19. Pagar un pedido");
            System.out.println("s. Salir");
            System.out.print("Opción: ");
            choice = keyboard.nextLine();

            switch (choice) {
                case "1":
                    addRestaurant();
                    break;
                case "2":
                    searchRestaurant();
                    break;
                case "3":
                    deleteRestaurant();
                    break;
                case "4":
                    modifyRestaurant();
                    break;
                case "5":
                    showRestaurantCatalog();
                    break;
                case "6":
                    addDish();
                    break;
                case "7":
                    searchDish();
                    break;
                case "8":
                    deleteDish();
                    break;
                case "9":
                    modifyDish();
                    break;
                case "10":
                    showDishCatalog();
                    break;
                case "11":
                    addUser();
                    break;
                case "12":
                    modifyUser();
                    break;
                case "13":
                    deleteUser();
                    break;
                case "14":
                    showUsers();
                    break;
                case "15":
                    login();
                    break;
                case "16":
                    addOrder();
                    break;
                case "17":
                    showOrderDetails();
                    break;
                case "18":
                    showNotPaidOrders();
                    break;
                case "19":
                    payOrder();
                    break;

            }
        } while (!choice.equals("s"));
    }

    private void login() {
        System.out.print("¿Cuál es tu usuario? ");
        String username = "EduJimCue";
        System.out.print("¿Cuál es tu contraseña? ");
        String password = "1234";

        UserDao userDao = new UserDao(connection);
        try {
            currentUser = userDao.getUser(username, password)
                    .orElseThrow(UserNotFoundException::new);
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
            System.exit(0);
        } catch (UserNotFoundException unfe) {
            System.out.println("No se ha encontrado al usuario,asegurate que los datos son correctos");
            System.exit(0);
        }
    }

    public void addRestaurant() {

        System.out.print("Nombre del restaurante: ");
        String name = keyboard.nextLine();
        System.out.print("Direccion: ");
        String adress = keyboard.nextLine();
        System.out.print("Codigo postal: ");
        int postalCode = Integer.parseInt(keyboard.nextLine());
        RestaurantDao restaurantDao = new RestaurantDao(connection);
        Restaurant restaurant = new Restaurant(name, postalCode,adress);

        try {
            restaurantDao.add(restaurant);
            System.out.println("El restaurante se ha añadido correctamente");
        } catch (SQLException  sqle) {
            sqle.printStackTrace();
            System.out.println("No se ha podido añadir el plato. Inténtelo de nuevo");
        }
        catch (RestaurantAlreadyExistException raee) {
            System.out.println("El restaurante ya existe en la base de datos");
    }
    }

    public void searchRestaurant() {
        System.out.print("Búsqueda por nombre: ");
        String name = keyboard.nextLine();

        RestaurantDao restaurantDao = new RestaurantDao(connection);
        try {
            Restaurant restaurant = restaurantDao.findByName(name);
            System.out.println(restaurant.getId());
            System.out.println(restaurant.getName());
            System.out.println(restaurant.getAdress());
            System.out.println(restaurant.getPostal_code());
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        } catch (RestaurantNotFoundException restaurantNotFoundException) {
            System.out.println("No se ha encontrado el restaurante");
        }
    }

    public void deleteRestaurant() {
        System.out.print("Nombre del restaurante a eliminar: ");
        String name = keyboard.nextLine();
        RestaurantDao restaurantDao = new RestaurantDao(connection);
        try {
            boolean deleted = restaurantDao.delete(name);
            if (deleted)
                System.out.println("El restaurante se ha eliminado correctamente");
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        }catch (RestaurantNotFoundException rnfe) {
            System.out.println("No se ha encontrado el restaurante");
        }
    }

    public void modifyRestaurant()  {
        System.out.print("Nombre del restaurante a modificar: ");
        String name = keyboard.nextLine();
        RestaurantDao restaurantDao = new RestaurantDao(connection);

        System.out.print("Nuevo Nombre: ");
        String newName = keyboard.nextLine();
        System.out.print("Nuevo Codigo Postal: ");
        int newPostalCode = Integer.parseInt(keyboard.nextLine());
        System.out.print("Nueva Direccion: ");
        String newAdress = keyboard.nextLine();
        Restaurant newRestaurant = new Restaurant(newName.trim(), newPostalCode, newAdress.trim());

        try {
            boolean modified = restaurantDao.modify(name, newRestaurant);
            if (modified)
                System.out.println("Los datos del restaurante se han modificado correctamente");
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        }catch (RestaurantNotFoundException rnfe){
            System.out.println("No se ha encontrado el restaurante");
        }
    }


    public void showRestaurantCatalog() {
        RestaurantDao restaurantDao = new RestaurantDao(connection);
        try {
            ArrayList<Restaurant> restaurants = restaurantDao.findAll();
            for (Restaurant restaurant : restaurants) {
                System.out.println(restaurant.getName());
            }
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        }
    }

    public void addDish() throws SQLException {
        DishDao dishDao = new DishDao(connection);

        System.out.print("Precio del plato: ");
        float price =Float.parseFloat(keyboard.nextLine());
        System.out.print("Nombre del plato: ");
        String name = keyboard.nextLine();
        System.out.print("Nombre del restaurante que lo sirve: ");
        String restaurantName = keyboard.nextLine();
        RestaurantDao restaurantDao = new RestaurantDao(connection);
        Restaurant restaurant;
        try {
            restaurant = restaurantDao.findByName(restaurantName);
        } catch (RestaurantNotFoundException rnfe) {
            System.out.println("El restaurante no existe");
            return;
        }
        Dish dish = new Dish(price, name, restaurant);

        try {
            dishDao.add(dish,restaurant);
            System.out.println("El plato se ha añadido correctamente");
        } catch (SQLException sqle) {
            System.out.println("No se ha podido añadir el plato. Inténtelo de nuevo");
        }
    }

    public void searchDish() {
        System.out.print("Búsqueda por nombre del plato: ");
        String name = keyboard.nextLine();

        DishDao dishDao = new DishDao(connection);

        try {
            Dish dish = dishDao.findByDishName(name);
            System.out.println(dish.getId());
            System.out.println(dish.getName());
            System.out.println(dish.getPrice());
            System.out.println(dish.getRestaurantName());
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        } catch (DishNotFoundException dnfe) {
            System.out.println("No se ha encontrado el plato");
        }
    }

    public void deleteDish() {
        System.out.print("Nombre del plato a eliminar: ");
        String name = keyboard.nextLine();
        DishDao dishDao = new DishDao(connection);
        try {
            boolean deleted = dishDao.delete(name);
            if (deleted)
                System.out.println("El plato se ha eliminado correctamente");
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        }catch (DishNotFoundException rnfe) {
            System.out.println("No se ha encontrado el plato");
        }
    }

    public void modifyDish() throws SQLException, RestaurantNotFoundException {
        System.out.print("Nombre del plato a modificar: ");
        String name = keyboard.nextLine();
        DishDao dishDao = new DishDao(connection);
        try {
            dishDao.findByDishName(name);
        } catch (DishNotFoundException | SQLException e) {
            System.out.println("No se ha encontrado el plato");
            return;
        }
        System.out.println("Nombre del restaurante que lo sirve:");
        String restaurant = keyboard.nextLine();
        RestaurantDao restaurantDao = new RestaurantDao(connection);
        try {
            restaurantDao.findByName(restaurant);
        } catch (RestaurantNotFoundException | SQLException restaurantNotFoundException) {
            System.out.println("No se ha encontrado el restaurante");
            return;
        }
        try {

           if (dishDao.findByOptionalNameAndRestaurant(name, restaurant).isEmpty()){
               System.out.println("No existe ese plato en ese restaurante");
               return;
           }
        } catch (DishNotFoundException | SQLException e) {
            System.out.println("No se ha encontrado un plato con ese restaurante");
            return;
        }

        System.out.print("Nuevo Nombre: ");
        String newName = keyboard.nextLine();
        System.out.print("Nuevo Precio: ");
        float newPrice = Float.parseFloat(keyboard.nextLine());
        Dish newDish = new Dish(newPrice, newName.trim());
        int restaurantId = restaurantDao.findByName(restaurant).getId();

        try {
            boolean modified = dishDao.modify(name,restaurantId, newDish);
            if (modified)
                System.out.println("Los datos del plato se han modificado correctamente");
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        }
    }

    public void showDishCatalog() {
        DishDao dishDao = new DishDao(connection);
        try {
            ArrayList<Dish> dishes = dishDao.findAll();
            for (Dish dish : dishes) {
                System.out.println(dish.getName()+" /"+dish.getPrice()+"€ /"+dish.getRestaurantName());
            }
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        }
    }

    private void addUser() {
        UserDao userDao = new UserDao(connection);
        System.out.print("Introduce tu nombre: ");
        String name = keyboard.nextLine();
        System.out.print("Introduce el username: ");
        String username = keyboard.nextLine();
        System.out.print("Introduce la contraseña: ");
        String password = keyboard.nextLine();
        User user = new User(name,username,password);
        try {
            userDao.add(user);
            System.out.println("Se ha registrado el usuario correctamente" );
        } catch (SQLException sqle) {
            System.out.println("Error al comunicar con la base de datos");
        }catch (UserAlreadyExistException uaee) {
            System.out.println("Ya existe un usuario con ese username");
        }
    }

    public void deleteUser() {
        System.out.print("Username del usuario a eliminar: ");
        String username = keyboard.nextLine();
        UserDao userDao = new UserDao(connection);
        try {
            boolean deleted = userDao.delete(username);
            if (deleted)
                System.out.println("El usuario se ha eliminado correctamente");
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        }catch (UserNotFoundException unfe) {
            System.out.println("No se ha encontrado el usuario");
        }
    }

    public void modifyUser()  {
        System.out.print("Username a modificar: ");
        String username = keyboard.nextLine();
        System.out.println("Password del usuario a modificar: ");
        String password = keyboard.nextLine();
        UserDao userDao= new UserDao(connection);
        User oldUser = new User();
        try {
            oldUser = userDao.getUser(username, password)
                    .orElseThrow(UserNotFoundException::new);
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        } catch (UserNotFoundException unfe) {
            System.out.println("No se ha encontrado al usuario,asegurate que los datos son correctos");
            return;
        }

        System.out.print("Nuevo Nombre: ");
        String newName = keyboard.nextLine();
        System.out.print("Nueva password: ");
        String newPassword = keyboard.nextLine();
        System.out.print("Nuevo username: ");
        String newUsername = keyboard.nextLine();
        User newUser = new User(newName.trim(), newPassword.trim(), newUsername.trim());

        try {
            boolean modified = userDao.modify(oldUser, newUser);
            if (modified)
                System.out.println("Los datos del usuario se han modificado correctamente");
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        }
    }

    public void showUsers() {
        UserDao userDao = new UserDao(connection);
        try {
            ArrayList<User> users = userDao.findAll();
            for (User user : users) {
                System.out.println(user.getUsername());
            }
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        }
    }

    private void addOrder() throws SQLException {
        System.out.print("¿A que restaurante quieres encargar?");
        String restaurant = keyboard.nextLine();
        RestaurantDao restaurantDao = new RestaurantDao(connection);
        try {
            restaurantDao.findByName(restaurant);
        } catch (RestaurantNotFoundException rnfe) {
           System.out.println("No se ha encontrado el restaurante");
        }

        DishDao dishDao = new DishDao(connection);
        ArrayList<Dish> dishes = dishDao.findbyRestaurant(restaurant);
        for (Dish dish : dishes) {
            System.out.println(dish.getName());
        }

        System.out.print("¿Qué platos quieres encargar? (separados por comas) ");
        String orderDishes = keyboard.nextLine();

        try {
            String[] dishArray = orderDishes.split(",");

            ArrayList <Dish> dishList = new ArrayList<>();
            for (String dishName : dishArray) {
                Dish dish1 = dishDao.findByNameAndRestaurant(dishName.trim(),restaurant);

                dishList.add(dish1);
            }

            OrderDao orderDao = new OrderDao(connection);
            orderDao.addOrder(currentUser, dishList);
            System.out.println("El pedido se ha creado correctamente");
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
            sqle.printStackTrace();
        } catch (DishNotFoundException e) {
            System.out.println("No se ha encontrado el plato");
        }
    }


    private void showOrderDetails() {
        OrderDao orderDao = new OrderDao(connection);
        System.out.println("Cual es la id del pedido que quieres buscar? :");
        int order = Integer.parseInt(keyboard.nextLine());

        try {
           List<Order> orders = orderDao.getOrder(order, currentUser.getId());
            for (Order order1 : orders) {
                System.out.println("Numero de pedido: " + order1.getId() +", Nombre del plato: " + order1.getDishName());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }catch (OrderNotFoundException onfe) {
            System.out.println("El pedido no existe");
        }
    }

    private void showNotPaidOrders() {
        OrderDao orderDao = new OrderDao(connection);

        try {
            Optional<List<Order>> orders = orderDao.getNotPaidOrder(currentUser.getId());
            if (orders.isPresent()){
                System.out.println("Los pedidos pendientes de pago son:");
                for (Order order1 : orders.orElseThrow(OrderNotFoundException::new)) {
                    System.out.println("Numero de pedido: " + order1.getId() );
            }
            }else{
                System.out.println("No hay pedidos pendientes de pago para este usuario");
            }
        } catch (SQLException | OrderNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void payOrder() {
        OrderDao orderDao = new OrderDao(connection);
        System.out.println("Cual es la id del pedido que quieres buscar? :");
        int order = Integer.parseInt(keyboard.nextLine());
        try {
            boolean modified = orderDao.payOrder(order);;
            if (modified)
                System.out.println("Se ha pagado el pedido correctamente");
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        }catch (OrderNotFoundException onfe) {
            System.out.println("No se ha encontrado el pedido");
        }
    }

}
