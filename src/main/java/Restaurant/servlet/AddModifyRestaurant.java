package Restaurant.servlet;

import Restaurant.dao.Database;
import Restaurant.dao.RestaurantDao;
import Restaurant.domain.Restaurant;
import Restaurant.domain.User;
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
@WebServlet("/add-restaurant")
public class AddModifyRestaurant extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        String name = request.getParameter("name");       // input name="title" del formulario
        int postalCode = Integer.parseInt(request.getParameter("postalCode"));
        String adress = request.getParameter("adress");
        Restaurant restaurant = new Restaurant(name,postalCode, adress);
        String action = request.getParameter("action");
        String restaurantName = request.getParameter("restaurantName");
        Database database = new Database();
        RestaurantDao restaurantDao = new RestaurantDao(database.getConnection());
        try {
            if (action.equals("register")) {
                restaurantDao.add(restaurant);
                out.println("<div class='alert alert-success' role='alert'>El restaurante se ha registrado correctamente</div>");
            } else {
                restaurantDao.modify(restaurantName, restaurant);
                out.println("<div class='alert alert-success' role='alert'>El restaurante se ha modificado correctamente</div>");
            }
        } catch (RestaurantAlreadyExistException baee) {
            out.println("<div class='alert alert-danger' role='alert'>El restaurante ya existe en la base de datos</div>");
            baee.printStackTrace();
        } catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'>Se ha producido un error al registrar el libro</div>");
            sqle.printStackTrace();
        } catch (RestaurantNotFoundException restaurantNotFoundException) {
            out.println("<div class='alert alert-danger' role='alert'>No se ha encontrado el restaurante</div>");
            restaurantNotFoundException.printStackTrace();
        }
    }
}
