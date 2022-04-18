package Restaurant.dao;

import Restaurant.domain.Dish;
import Restaurant.domain.Restaurant;
import Restaurant.exceptions.DishNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class DishDao {

    private Connection connection;

    public DishDao(Connection connection) {
        this.connection = connection;
    }

    public void add(Dish dish, Restaurant restaurant) throws SQLException {
        String sql = "INSERT INTO DISH (DISH_NAME, PRICE,ID_RESTAURANT) VALUES (?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, dish.getName());
        statement.setFloat(2, dish.getPrice());
        statement.setFloat(3, restaurant.getId());
        statement.executeUpdate();
    }


    public boolean delete(String name) throws SQLException, DishNotFoundException {
        if (!existDish(name))
            throw new DishNotFoundException();
        String sql = "DELETE FROM DISH WHERE DISH_NAME = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        int rows = statement.executeUpdate();

        return rows == 1;
    }

    public boolean modify(String name, int restaurantId, Dish dish) throws SQLException {
        String sql = "UPDATE DISH SET DISH_NAME = ?, PRICE = ?, ID_RESTAURANT=? WHERE DISH_NAME = ? AND ID_RESTAURANT = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, dish.getName());
        statement.setFloat(2, dish.getPrice());
        statement.setInt(3,dish.getRestaurant().getId());
        statement.setString(4, name);
        statement.setInt(5, restaurantId);
        int rows = statement.executeUpdate();
        return rows == 1;
    }

    public ArrayList<Dish> findAll() throws SQLException {
        String sql = "SELECT * FROM DISH JOIN RESTAURANT ON RESTAURANT.ID_RESTAURANT = DISH.ID_RESTAURANT ORDER BY RESTAURANT_NAME";
        ArrayList<Dish> dishes = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Dish dish = new Dish();
            dish.setId(resultSet.getInt("ID_DISH"));
            dish.setName(resultSet.getString("DISH_NAME"));
            dish.setPrice(resultSet.getInt("PRICE"));
            dish.setRestaurantName(resultSet.getString("RESTAURANT_NAME"));
            dishes.add(dish);
        }

        return dishes;
    }
    public ArrayList<Dish> findbyText(String text) throws SQLException {
        String sql = "SELECT DISH_NAME ,RESTAURANT_NAME FROM DISH JOIN RESTAURANT  ON restaurant.id_restaurant = dish.id_restaurant WHERE INSTR(RESTAURANT_NAME,?) != 0  OR INSTR(DISH_NAME, ?) != 0 ORDER BY DISH_NAME";
        ArrayList<Dish> dishes = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, text);
        statement.setString(2, text);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Dish dish = new Dish();
            dish.setName(resultSet.getString("DISH_NAME"));
            dish.setRestaurantName(resultSet.getString("RESTAURANT_NAME"));
            dishes.add(dish);
        }

        return dishes;
    }


    public ArrayList<Dish> findbyRestaurant(String name) throws SQLException {
        String sql =  "SELECT * FROM DISH JOIN RESTAURANT ON RESTAURANT.ID_RESTAURANT = DISH.ID_RESTAURANT WHERE RESTAURANT_NAME = ?";

        ArrayList<Dish> dishes = new ArrayList<>();


        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Dish dish = new Dish();
            dish.setId(resultSet.getInt("ID_DISH"));
            dish.setName(resultSet.getString("DISH_NAME"));
            dish.setPrice(resultSet.getFloat("PRICE"));

            dishes.add(dish);
        }

        return dishes;
    }
    public Dish findByDishName(String name) throws SQLException, DishNotFoundException {
        if (!existDish(name))
            throw new DishNotFoundException();
        String sql = "SELECT * FROM DISH JOIN RESTAURANT ON RESTAURANT.ID_RESTAURANT = DISH.ID_RESTAURANT WHERE DISH_NAME = ?";
        Dish dish = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            dish = new Dish();
            dish.setId(resultSet.getInt("ID_DISH"));
            dish.setPrice(resultSet.getInt("PRICE"));
            dish.setName(resultSet.getString("DISH_NAME"));
            dish.setRestaurantName(resultSet.getString("RESTAURANT_NAME"));
        }

        return dish;
    }


    public Optional<Dish> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM DISH WHERE DISH_NAME = ?";
        Dish dish = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            dish = new Dish();
            dish.setId(resultSet.getInt("ID_DISH"));
            dish.setPrice(resultSet.getInt("PRICE"));
            dish.setName(resultSet.getString("DISH_NAME"));
        }

        return Optional.ofNullable(dish);
    }
    public Dish findByNameAndRestaurant(String name, String restaurant) throws SQLException, DishNotFoundException {
        if (!existDish(name))
            throw new DishNotFoundException();
        String sql = "SELECT * FROM DISH JOIN RESTAURANT ON RESTAURANT.ID_RESTAURANT = DISH.ID_RESTAURANT WHERE DISH_NAME = ? AND RESTAURANT_NAME = ? ";
        Dish dish = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setString(2, restaurant);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            dish = new Dish();
            dish.setId(resultSet.getInt("ID_DISH"));
            dish.setPrice(resultSet.getInt("PRICE"));
            dish.setName(resultSet.getString("DISH_NAME"));
        }

        return dish;
    }

    public Optional<Dish> findByOptionalNameAndRestaurant(String name, String restaurant) throws SQLException, DishNotFoundException {
        if (!existDish(name))
            throw new DishNotFoundException();
        String sql = "SELECT * FROM DISH JOIN RESTAURANT ON RESTAURANT.ID_RESTAURANT = DISH.ID_RESTAURANT WHERE DISH_NAME = ? AND RESTAURANT_NAME = ? ";
        Dish dish = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setString(2, restaurant);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            dish = new Dish();
            dish.setId(resultSet.getInt("ID_DISH"));
            dish.setPrice(resultSet.getInt("PRICE"));
            dish.setName(resultSet.getString("DISH_NAME"));
        }

        return Optional.ofNullable(dish);
    }

    public boolean existDish(String name) throws SQLException {
        Optional<Dish> dish = findByName(name);
        return dish.isPresent();
    }

}
