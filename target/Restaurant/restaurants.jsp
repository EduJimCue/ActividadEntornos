<%@ page import="Restaurant.dao.Database" %>
<%@ page import="Restaurant.dao.RestaurantDao" %>
<%@ page import="Restaurant.domain.Restaurant" %>
<%@ page import="Restaurant.domain.User" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<%
    User currentUser = (User) session.getAttribute("currentUser");
%>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
    <div class="container">
        <h2>Listado de Restaurantes</h2>
        <ul class="list-group">
            <%
                Database database = new Database();
                RestaurantDao restaurantDao = new RestaurantDao(database.getConnection());
                try {
                             ArrayList<Restaurant> restaurants = restaurantDao.findAll();
                             for (Restaurant restaurant : restaurants) {
            %>
                        <li class="list-group-item">
                        Restaurante: <a target="_blank" href="restaurant.jsp?name=<%= restaurant.getName() %>"><%= restaurant.getName() %></a> Direccion: <%= restaurant.getAdress() %>
                        <%
                        if ((currentUser != null) && (currentUser.getRole().equals("admin"))) {
                        %>
                                <a href="addrestaurant.jsp?name=<%= restaurant.getName() %>" class="btn btn-outline-warning">Modificar</a>
                                <a href="delete.jsp?name=<%= restaurant.getName() %>" class="btn btn-outline-danger">Eliminar</a>
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