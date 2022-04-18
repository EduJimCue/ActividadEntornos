package Restaurant.servlet;

import Restaurant.dao.Database;
import Restaurant.dao.DishDao;
import Restaurant.dao.OrderDao;
import Restaurant.dao.RestaurantDao;
import Restaurant.domain.Dish;
import Restaurant.domain.Restaurant;
import Restaurant.domain.User;
import Restaurant.exceptions.DishNotFoundException;
import Restaurant.exceptions.RestaurantAlreadyExistException;
import Restaurant.exceptions.RestaurantNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/add-order")
public class AddOrder extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        User currentUser = (User) request.getSession().getAttribute("currentUser");


        String dishName = request.getParameter("dish");
        String restaurantName = request.getParameter("restaurant");

        Optional<Dish> dish = null;
        Database database = new Database();
        DishDao dishDao = new DishDao(database.getConnection());
        RestaurantDao restaurantDao = new RestaurantDao(database.getConnection());
        try {
            restaurantDao.findByName(restaurantName);
        } catch (SQLException throwables) {
            out.println("<div class='alert alert-danger' role='alert'>Se ha producido un error al registrar el pedido</div>");
            throwables.printStackTrace();
        } catch (RestaurantNotFoundException rnfe) {
            out.println("<div class='alert alert-danger' role='alert'>No se ha encontrado el restaurante indicado</div>");
            rnfe.printStackTrace();
        }

        try {
           dish = dishDao.findByOptionalNameAndRestaurant(dishName,restaurantName);
        } catch (SQLException throwables) {
            out.println("<div class='alert alert-danger' role='alert'>Se ha producido un error al registrar el pedido</div>");
            throwables.printStackTrace();
        } catch (DishNotFoundException e) {
            out.println("<div class='alert alert-danger' role='alert'>No se ha encontrado el plato indicado</div>");
            e.printStackTrace();
        }

        if (dish!=null){
        try {
            OrderDao orderDao=new OrderDao(database.getConnection());
            orderDao.addOpcionalOrder(currentUser,List.of(dish));
            out.println("<div class='alert alert-success' role='alert'>El pedido se ha registrado correctamente</div>");
        } catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'>Se ha producido un error al registrar el pedido</div>");
            sqle.printStackTrace();
        }
        }else{
            out.println("<div class='alert alert-danger' role='alert'>Comprueba los datos </div>");
        }
    }
}
