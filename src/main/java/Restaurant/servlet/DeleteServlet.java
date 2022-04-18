package Restaurant.servlet;

import Restaurant.dao.Database;
import Restaurant.dao.RestaurantDao;
import Restaurant.domain.User;
import Restaurant.exceptions.RestaurantNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/delete-restaurant")
public class DeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("index.jsp");
        }

        String name = request.getParameter("restaurantName");

        Database database = new Database();
        RestaurantDao restaurantDao = new RestaurantDao(database.getConnection());
        try {
            restaurantDao.delete(name);
            out.println("<div class='alert alert-success' role='alert'>El restaurante se ha eliminado correctamente</div>");
        } catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'>Se ha producido un error al borrar el restaurante, asegurate de que no tenga platos asignados</div>"+
                   "Comprueba el restaurante: "+ "<a target='_blank' href='restaurant.jsp?name="+name+"'>" +name+"</a>");
            sqle.printStackTrace();
        } catch (RestaurantNotFoundException restaurantNotFoundException) {
            restaurantNotFoundException.printStackTrace();
            out.println("<div class='alert alert-danger' role='alert'>No se ha encontrado el restaurante</div>");
        }
    }
}
