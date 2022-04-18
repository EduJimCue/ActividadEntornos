package Restaurant.servlet;

import Restaurant.dao.Database;
import Restaurant.dao.DishDao;
import Restaurant.dao.RestaurantDao;
import Restaurant.domain.User;
import Restaurant.exceptions.DishNotFoundException;
import Restaurant.exceptions.RestaurantNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/delete-dish")
public class DeleteDishServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("index.jsp");
        }

        String name = request.getParameter("dishName");

        Database database = new Database();
        DishDao dishDao = new DishDao(database.getConnection());
        try {
            dishDao.delete(name);
            out.println("<div class='alert alert-success' role='alert'>El plato se ha eliminado correctamente</div>");
        } catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'>Se ha producido un error al borrar el plato, asegurate de que no tenga pedidos asignados</div>");
            sqle.printStackTrace();
        } catch (DishNotFoundException dnfe) {
            dnfe.printStackTrace();
            out.println("<div class='alert alert-danger' role='alert'>No se ha encontrado el restaurante</div>");
        }
    }
}
