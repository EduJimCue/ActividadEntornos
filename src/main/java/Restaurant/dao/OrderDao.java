package Restaurant.dao;


import Restaurant.domain.Dish;
import Restaurant.domain.Order;
import Restaurant.domain.User;
import Restaurant.exceptions.OrderNotFoundException;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDao {

    private Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    public void addOrder(User user, List <Dish> dishes) throws SQLException {
        String orderSql = "INSERT INTO ORDERS (ORDER_DATE, PAID, ID_USER) VALUES (?, ?, ?)";

        PreparedStatement orderStatement = connection.prepareStatement(orderSql);
        orderStatement.setDate(1, Date.valueOf(LocalDate.now()));
        orderStatement.setInt(2, 0);
        orderStatement.setInt(3,user.getId() );
        orderStatement.executeUpdate();

        String sql = "SELECT MAX(ID_ORDER) FROM orders";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            int orderId = resultSet.getInt(1);

        for (Dish dish : dishes) {
            String bookSql = "INSERT INTO ORDERS_DISH (ID_DISH, ID_ORDER) VALUES (?, ?)";
            PreparedStatement bookStatement = connection.prepareStatement(bookSql);
            bookStatement.setInt(2, orderId);
            bookStatement.setInt(1, dish.getId());
            bookStatement.executeUpdate();
            }
        }
    }

    public void delete(int idOrder ) throws SQLException {
        String orderSql = "DELETE FROM ORDERS_DISH WHERE ID_ORDER=?";
        PreparedStatement orderStatement = connection.prepareStatement(orderSql);
        orderStatement.setInt(1,idOrder);
        orderStatement.executeUpdate();

        String sql = "DELETE FROM ORDERS WHERE ID_ORDER=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,idOrder);
        statement.executeUpdate();

    }

    public void addOpcionalOrder(User user, List <Optional<Dish>> dishes) throws SQLException {
        String orderSql = "INSERT INTO ORDERS (ORDER_DATE, PAID, ID_USER) VALUES (?, ?, ?)";

        PreparedStatement orderStatement = connection.prepareStatement(orderSql);
        orderStatement.setDate(1, Date.valueOf(LocalDate.now()));
        orderStatement.setInt(2, 0);
        orderStatement.setInt(3,user.getId() );
        orderStatement.executeUpdate();

        String sql = "SELECT MAX(ID_ORDER) FROM orders";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            int orderId = resultSet.getInt(1);

            for (Optional <Dish> dish : dishes) {
                String bookSql = "INSERT INTO ORDERS_DISH (ID_DISH, ID_ORDER) VALUES (?, ?)";
                PreparedStatement bookStatement = connection.prepareStatement(bookSql);
                bookStatement.setInt(2, orderId);
                bookStatement.setInt(1, dish.get().getId());
                bookStatement.executeUpdate();
            }
        }
    }

    public List<Order> getOrder(int idOrder, int idUser) throws SQLException, OrderNotFoundException {
        if (!existOrder(idOrder))
            throw new OrderNotFoundException();

        String sql = "SELECT * FROM ORDERS O JOIN ORDERS_DISH ON O.id_order=orders_dish.id_order JOIN DISH ON DISH.ID_DISH = ORDERS_DISH.ID_DISH WHERE ID_USER=? AND O.ID_ORDER= ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idUser);
        statement.setInt(2,idOrder);
        ArrayList<Order> orders = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
            Order order = new Order();
            order.setId(resultSet.getInt("ID_ORDER"));
            order.setPaid(resultSet.getBoolean("PAID"));
            order.setDate(resultSet.getDate("ORDER_DATE"));
            order.setDishName(resultSet.getString("DISH_NAME"));
            orders.add(order);
        }
        return orders;

    }

    public ArrayList<Order> getAllOrders() throws SQLException {


        String sql = "SELECT * FROM ORDERS O JOIN ORDERS_DISH ON O.id_order=orders_dish.id_order JOIN DISH ON DISH.ID_DISH = ORDERS_DISH.ID_DISH JOIN USSR ON USSR.ID_USER = O.ID_USER ORDER BY O.ID_ORDER ";
        PreparedStatement statement = connection.prepareStatement(sql);
        ArrayList<Order> orders = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
            Order order = new Order();
            order.setId(resultSet.getInt("ID_ORDER"));
            order.setPaid(resultSet.getBoolean("PAID"));
            order.setDate(resultSet.getDate("ORDER_DATE"));
            order.setDishName(resultSet.getString("DISH_NAME"));
            order.setDishPrice(resultSet.getFloat("PRICE"));
            order.setOrderUser(resultSet.getString("USUARIO"));

            orders.add(order);
        }
        return orders;

    }

    public ArrayList<Order> getUserOrder(int idUser) throws SQLException {


        String sql = "SELECT * FROM ORDERS O JOIN ORDERS_DISH ON O.id_order=orders_dish.id_order JOIN DISH ON DISH.ID_DISH = ORDERS_DISH.ID_DISH WHERE ID_USER=? ORDER BY O.ID_ORDER ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idUser);
        ArrayList<Order> orders = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
            Order order = new Order();
            order.setId(resultSet.getInt("ID_ORDER"));
            order.setPaid(resultSet.getBoolean("PAID"));
            order.setDate(resultSet.getDate("ORDER_DATE"));
            order.setDishName(resultSet.getString("DISH_NAME"));
            order.setDishPrice(resultSet.getFloat("PRICE"));
            orders.add(order);
        }
        return orders;

    }

    public Optional<List<Order>> getNotPaidOrder(int idUser) throws SQLException{

        String sql = "SELECT * FROM ORDERS WHERE ID_USER=? AND PAID= 0";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idUser);
        ArrayList<Order> orders = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
            Order order = new Order();
            order.setId(resultSet.getInt("ID_ORDER"));
            order.setPaid(resultSet.getBoolean("PAID"));
            order.setDate(resultSet.getDate("ORDER_DATE"));
            orders.add(order);
        }
        return Optional.of(orders);

    }

    public boolean payOrder(int idOrder) throws SQLException, OrderNotFoundException {
        if (!existOrder(idOrder))
            throw new OrderNotFoundException();
        String sql = "UPDATE ORDERS SET PAID = 1 WHERE ID_ORDER = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idOrder);
        int rows = statement.executeUpdate();
        return rows == 1;
    }

    public Optional<Order> existById(int idOrder) throws SQLException {
        String sql = "SELECT * FROM ORDERS WHERE ID_ORDER = ?";
        Order order = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idOrder);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            order = new Order();
            order.setId(resultSet.getInt("ID_ORDER"));

        }

        return Optional.ofNullable(order);
    }

    public boolean existOrder(int idOrder) throws SQLException {
        Optional<Order> order = existById(idOrder);
        return order.isPresent();
    }


}
