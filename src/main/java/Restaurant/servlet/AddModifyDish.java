package Restaurant.servlet;

import Restaurant.dao.Database;
import Restaurant.dao.DishDao;
import Restaurant.dao.RestaurantDao;
import Restaurant.domain.Dish;
import Restaurant.domain.Restaurant;
import Restaurant.exceptions.RestaurantNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/add-dish")
public class AddModifyDish extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Database database = new Database();

        String name = request.getParameter("name");       // input name="title" del formulario
        float price = Float.parseFloat(request.getParameter("price"));
        String restaurant = request.getParameter("restaurant");
        RestaurantDao restaurantDao =new RestaurantDao(database.getConnection());
        Restaurant newRestaurant = null;


        try{
            newRestaurant = restaurantDao.findByName(restaurant);
        }catch (RestaurantNotFoundException restaurantNotFoundException) {
            out.println("<div class='alert alert-danger' role='alert'>No se ha encontrado el restaurante</div>");
            restaurantNotFoundException.printStackTrace();
        } catch (SQLException throwables) {
            out.println("<div class='alert alert-danger' role='alert'>Fallo de comunicacion</div>");
            throwables.printStackTrace();
        }
        Dish dish = new Dish(price,name, newRestaurant);
        String action = request.getParameter("action");
        String dishName = request.getParameter("dishName");
        String restaurant1 =request.getParameter("oldRestaurant");
        DishDao dishDao = new DishDao(database.getConnection());
        Restaurant oldRestaurant= null;

        try {
            if (action.equals("register")) {
                dishDao.add(dish, newRestaurant);
                out.println("<div class='alert alert-success' role='alert'>El plato se ha registrado correctamente</div>");
            } else {
                try{
                    oldRestaurant = restaurantDao.findByName(restaurant1);
                }catch (RestaurantNotFoundException restaurantNotFoundException) {
                    out.println("<div class='alert alert-danger' role='alert'>No se ha encontrado el restaurante</div>");
                    restaurantNotFoundException.printStackTrace();
                } catch (SQLException throwables) {
                    out.println("<div class='alert alert-danger' role='alert'>Fallo de comunicacion</div>");
                    throwables.printStackTrace();
                }
                dishDao.modify(dishName, oldRestaurant.getId(), dish);
                out.println("<div class='alert alert-success' role='alert'>El plato se ha modificado correctamente</div>");
            }
        }  catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'>Se ha producido un error al registrar el plato</div>");
            sqle.printStackTrace();
        }
    }
}
