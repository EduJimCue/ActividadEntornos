package Restaurant.dao;

import Restaurant.domain.Restaurant;
import Restaurant.exceptions.RestaurantAlreadyExistException;
import Restaurant.exceptions.RestaurantNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class RestaurantDao {

    private Connection connection;

    public RestaurantDao(Connection connection) {
        this.connection = connection;
    }

    public void add(Restaurant restaurant) throws SQLException, RestaurantAlreadyExistException {
        if (existRestaurant(restaurant.getName()))
            throw new RestaurantAlreadyExistException();

        String sql = "INSERT INTO RESTAURANT (RESTAURANT_NAME, POSTAL_CODE, ADRESS) VALUES (?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, restaurant.getName());
        statement.setInt(2,restaurant.getPostal_code());
        statement.setString(3, restaurant.getAdress());
        statement.executeUpdate();
    }

    public boolean delete(String name) throws SQLException,RestaurantNotFoundException {
        if (!existRestaurant(name))
            throw new RestaurantNotFoundException();
        String sql = "DELETE FROM RESTAURANT WHERE RESTAURANT_NAME = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        int rows = statement.executeUpdate();

        return rows == 1;
    }
    public void deleted(String name) throws SQLException,RestaurantNotFoundException {
        if (!existRestaurant(name))
            throw new RestaurantNotFoundException();
        String sql = "DELETE FROM RESTAURANT WHERE RESTAURANT_NAME = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.executeUpdate();

    }

    public boolean modify(String name, Restaurant restaurant) throws SQLException,RestaurantNotFoundException {
        if (!existRestaurant(name))
            throw new RestaurantNotFoundException();
        String sql = "UPDATE RESTAURANT SET RESTAURANT_NAME = ?, POSTAL_CODE = ?, ADRESS = ? WHERE RESTAURANT_NAME = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, restaurant.getName());
        statement.setInt(2, restaurant.getPostal_code());
        statement.setString(3, restaurant.getAdress());
        statement.setString(4, name);
        int rows = statement.executeUpdate();
        return rows == 1;
    }

    public ArrayList<Restaurant> findAll() throws SQLException {
        String sql = "SELECT * FROM RESTAURANT ORDER BY RESTAURANT_NAME";
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Restaurant restaurant = new Restaurant();
            restaurant.setId(resultSet.getInt("ID_RESTAURANT"));
            restaurant.setName(resultSet.getString("RESTAURANT_NAME"));
            restaurant.setPostal_code(resultSet.getInt("POSTAL_CODE"));
            restaurant.setAdress(resultSet.getString("ADRESS"));
            restaurants.add(restaurant);
        }

        return restaurants;
    }
    public ArrayList<Restaurant> findbyText(String text) throws SQLException {
        String sql = "SELECT DISTINCT RESTAURANT_NAME FROM RESTAURANT JOIN DISH ON restaurant.id_restaurant = dish.id_restaurant WHERE INSTR(DISH_NAME,?) != 0  OR INSTR(RESTAURANT_NAME,?) != 0 ORDER BY RESTAURANT_NAME";
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, text);
        statement.setString(2, text);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Restaurant restaurant = new Restaurant();
            restaurant.setName(resultSet.getString("RESTAURANT_NAME"));
            restaurants.add(restaurant);
        }

        return restaurants;
    }


    public Restaurant findByName(String name) throws SQLException, RestaurantNotFoundException {
        if (!existRestaurant(name))
            throw new RestaurantNotFoundException();

        String sql = "SELECT * FROM RESTAURANT WHERE RESTAURANT_NAME = ?";
        Restaurant restaurant = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            restaurant = new Restaurant();
            restaurant.setId(resultSet.getInt("ID_RESTAURANT"));
            restaurant.setName(resultSet.getString("RESTAURANT_NAME"));
            restaurant.setPostal_code(resultSet.getInt("POSTAL_CODE"));
            restaurant.setAdress(resultSet.getString("ADRESS"));
        }

        return restaurant;
    }


    public Optional<Restaurant> existByName(String name) throws SQLException {
        String sql = "SELECT * FROM RESTAURANT WHERE RESTAURANT_NAME = ?";
        Restaurant restaurant = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            restaurant = new Restaurant();
            restaurant.setId(resultSet.getInt("ID_RESTAURANT"));
            restaurant.setName(resultSet.getString("RESTAURANT_NAME"));
            restaurant.setPostal_code(resultSet.getInt("POSTAL_CODE"));
            restaurant.setAdress(resultSet.getString("ADRESS"));
        }

        return Optional.ofNullable(restaurant);
    }

    public boolean existRestaurant(String name) throws SQLException {
        Optional<Restaurant> restaurant = existByName(name);
        return restaurant.isPresent();
    }
}
