package Restaurant.servlet;

import Restaurant.dao.Database;
import Restaurant.dao.OrderDao;
import Restaurant.domain.User;
import Restaurant.exceptions.OrderNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/pay-order")
public class PayOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("index.jsp");
        }

        int orderId = Integer.parseInt(request.getParameter("orderId"));

        Database database = new Database();
        OrderDao orderDao = new OrderDao(database.getConnection());
        try {
            orderDao.payOrder(orderId);
            out.println("<div class='alert alert-success' role='alert'>El pedido se ha pagado correctamente</div>");
        } catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'>Se ha producido un error al borrar el pedido, asegurate de que no tenga platos asignados</div>");
            sqle.printStackTrace();
        } catch (OrderNotFoundException e) {
            out.println("<div class='alert alert-danger' role='alert'>No se ha encontrado el pedido a pagar</div>");
            e.printStackTrace();
        }
    }
}
