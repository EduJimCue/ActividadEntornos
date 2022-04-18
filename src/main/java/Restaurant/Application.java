package Restaurant;

import Restaurant.exceptions.RestaurantNotFoundException;

import java.sql.SQLException;

public class Application {
    public static void main(String args[]) throws SQLException, RestaurantNotFoundException {
        Menu menu = new Menu();
        menu.showMenu();
    }
}
