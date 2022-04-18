package Restaurant.servlet;

import Restaurant.dao.Database;
import Restaurant.dao.DishDao;
import Restaurant.domain.Dish;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/search-dish")
public class SearchDishServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String searchText = request.getParameter("text");

        Database database = new Database();
        DishDao dishDao = new DishDao(database.getConnection());
        try {
            ArrayList<Dish> dishes= dishDao.findbyText(searchText);
            StringBuilder result = new StringBuilder("<ul class='list-group'>");
            for (Dish dish : dishes) {
                result.append("<li class='list-group-item'> <a target='_blank' href='dish.jsp?name="+dish.getName()+ "'>").append (dish.getName()).append  ("</li>");
            }
            result.append("</ul>");
            out.println(result);
        } catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'>Se ha producido un error durante la búsqueda</div>");
            sqle.printStackTrace();
        }
    }

}
