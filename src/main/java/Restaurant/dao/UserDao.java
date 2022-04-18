package Restaurant.dao;

import Restaurant.domain.Restaurant;
import Restaurant.domain.User;
import Restaurant.exceptions.RestaurantNotFoundException;
import Restaurant.exceptions.UserAlreadyExistException;
import Restaurant.exceptions.UserNotFoundException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class UserDao {

    private Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public void add(User user) throws SQLException, UserAlreadyExistException {
        if (existUser(user.getUsername()))
            throw new UserAlreadyExistException();

        String sql = "INSERT INTO USSR (USUARIO, PSSWORD, USERNAME, ROLE) VALUES (?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getName());
        statement.setString(3, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setString(4,"user");
        statement.executeUpdate();
    }

    public boolean delete(String username) throws SQLException, UserNotFoundException {
        if (!existUser(username))
            throw new UserNotFoundException();
        String sql = "DELETE FROM USSR WHERE USERNAME = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        int rows = statement.executeUpdate();

        return rows == 1;
    }

    public boolean modify(User oldUser, User newUser) throws SQLException {

        String sql = "UPDATE USSR SET USUARIO = ?, PSSWORD = ?, USERNAME = ? WHERE USERNAME = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, newUser.getName());
        statement.setString(2, newUser.getPassword());
        statement.setString(3, newUser.getUsername());
        statement.setString(4, oldUser.getUsername());
        int rows = statement.executeUpdate();
        return rows == 1;
    }
    public boolean modifyApp(String userName, User newUser) throws SQLException {

        String sql = "UPDATE USSR SET USUARIO = ?, PSSWORD = ?, USERNAME = ? WHERE USERNAME = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, newUser.getName());
        statement.setString(2, newUser.getPassword());
        statement.setString(3, newUser.getUsername());
        statement.setString(4, userName);
        int rows = statement.executeUpdate();
        return rows == 1;
    }

    public Optional<User> getUser(String username, String password) throws SQLException, UserNotFoundException {
        if (!existUser(username))
            throw new UserNotFoundException();

        String sql = "SELECT * FROM USSR WHERE USERNAME = ? AND PSSWORD = ?";
        User user = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("ID_USER"));
            user.setName(resultSet.getString("USUARIO"));
            user.setUsername(resultSet.getString("USERNAME"));
            user.setPassword(resultSet.getString("PSSWORD"));
            user.setRole(resultSet.getString("ROLE"));

        }


        return Optional.ofNullable(user);
    }

    public ArrayList<User> findAll() throws SQLException {
        String sql = "SELECT * FROM USSR ORDER BY USERNAME";
        ArrayList<User> users = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("ID_USER"));
            user.setName(resultSet.getString("USUARIO"));
            user.setPassword(resultSet.getString("PSSWORD"));
            user.setUsername(resultSet.getString("USERNAME"));
            users.add(user);
        }

        return users;
    }

    public Optional<User> findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM USSR WHERE USERNAME = ?";
        User user = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("ID_USER"));
            user.setName(resultSet.getString("USUARIO"));
            user.setPassword(resultSet.getString("PSSWORD"));
            user.setUsername(resultSet.getString("USERNAME"));
        }

        return Optional.ofNullable(user);
    }
    public User findByAppUsername(String username) throws SQLException {
        String sql = "SELECT * FROM USSR WHERE USERNAME = ?";
        User user = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("ID_USER"));
            user.setName(resultSet.getString("USUARIO"));
            user.setPassword(resultSet.getString("PSSWORD"));
            user.setUsername(resultSet.getString("USERNAME"));
        }

        return user;
    }


    public boolean existUser(String username) throws SQLException {
        Optional<User> user = findByUsername(username);
        return user.isPresent();
    }

    public boolean existUsernamePassword(String username, String password) throws SQLException, UserNotFoundException {
        Optional<User> user = getUser(username, password);
        return user.isPresent();
    }
}
