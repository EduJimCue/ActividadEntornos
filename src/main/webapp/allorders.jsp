<%@ page import="Restaurant.dao.Database" %>
<%@ page import="Restaurant.dao.OrderDao" %>
<%@ page import="Restaurant.domain.Order" %>
<%@ page import="Restaurant.domain.User" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<%
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null) {
            response.sendRedirect("index.jsp");
        }
%>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
    <div class="container">
        <h2>Listado de mis pedidos</h2>
        <ul class="list-group">
            <%
                Database database = new Database();
                OrderDao orderDao = new OrderDao(database.getConnection());
                try {
                             ArrayList<Order> orders = orderDao.getAllOrders();
                             for (Order order : orders) {
            %>
                        <li class="list-group-item">
                        Numero de pedido: <%= order.getId() %></a> Fecha: <%= order.getDate() %> Plato:<%= order.getDishName() %> Precio:<%= order.getDishPrice() %>€ Usuario:<%= order.getOrderUser() %>
                        <%
                        if (!order.isPaid()) {
                        %>
                                <a href="payorder.jsp?orderId=<%= order.getId()%>&dishPrice=<%= order.getDishPrice()%>" class="btn btn-outline-warning">Pagar pedido</a>
                        <%
                            }else{
                        %>
                                <a href="deleteorder.jsp?orderId=<%= order.getId() %>" class="btn btn-outline-danger">Borrar pedido</a>
                        <%
                            }
                        %>
                        </li>
            <%
                    }
               } catch (SQLException sqle) {
            %>
                    <div class="alert alert-danger" role="alert">
                      Error conectando con la base de datos
                    </div>
            <%
               }
            %>
        </ul>
        <p><a href="index.jsp">Vuelve al inicio</a></p>
    </div>
</body>
</html>