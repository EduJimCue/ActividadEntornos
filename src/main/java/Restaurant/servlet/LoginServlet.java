package Restaurant.servlet;

import Restaurant.dao.Database;
import Restaurant.dao.UserDao;
import Restaurant.domain.User;
import Restaurant.exceptions.UserNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Database database = new Database();
        UserDao userDao = new UserDao(database.getConnection());
        try {
            Optional<User> user = userDao.getUser(username, password);
            if (user.isPresent()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("currentUser", user.get());
                out.println("<div class='alert alert-success' role='alert'>Login correcto</div>");
            } else {
                out.println("<div class='alert alert-danger' role='alert'>Datos introducidos incorrectos</div>");
            }
        } catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'>Se ha producido un error durante la búsqueda</div>");
            sqle.printStackTrace();
        }catch (UserNotFoundException unfe) {
            out.println("<div class='alert alert-danger' role='alert'>No se ha encontrado el usuario</div>");
        }
    }
}
