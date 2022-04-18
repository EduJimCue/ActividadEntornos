package Restaurant.servlet;

import Restaurant.dao.Database;
import Restaurant.dao.UserDao;
import Restaurant.domain.User;
import Restaurant.exceptions.UserAlreadyExistException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/add-user")
public class AddModifyUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        String name = request.getParameter("name");       // input name="title" del formulario
        String password = request.getParameter("password");
        String username = request.getParameter("username");
        User user = new User(name,password, username);
        String action = request.getParameter("action");
        String oldUser = request.getParameter("UserName");
        Database database = new Database();
        UserDao userDao = new UserDao(database.getConnection());
        try {
            if (action.equals("register")) {
                userDao.add(user);
                out.println("<div class='alert alert-success' role='alert'>El usuario se ha registrado correctamente</div>");
            } else {
                userDao.modifyApp(oldUser, user);
                out.println("<div class='alert alert-success' role='alert'>El usuario se ha modificado correctamente, para ver los cambios cierra sesion y vuelve a iniciar sesion</div>");
            }
        } catch (UserAlreadyExistException uaee) {
            out.println("<div class='alert alert-danger' role='alert'>El usuario ya existe en la base de datos</div>");
            uaee.printStackTrace();
        } catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'>Se ha producido un error al registrar el libro</div>");
            sqle.printStackTrace();
        }

    }
}
