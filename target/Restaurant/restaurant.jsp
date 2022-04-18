<%@ page import="Restaurant.dao.Database" %>
<%@ page import="Restaurant.dao.RestaurantDao" %>
<%@ page import="Restaurant.domain.Restaurant" %>
<%@ page import="Restaurant.dao.DishDao" %>
<%@ page import="Restaurant.domain.Dish" %>
<%@ page import= "Restaurant.exceptions.RestaurantNotFoundException" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>

<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
    <%
        String restaurantName = request.getParameter("name");
        Database db = new Database();
        RestaurantDao restaurantDao = new RestaurantDao(db.getConnection());

        try {
            Restaurant restaurant = restaurantDao.findByName(restaurantName);
    %>
    <div class="container">
    <div class="card text-center">
        <div class="card" style="width: 30rem; margin-right:auto; margin-left:auto; ">
          <img src="..." class="card-img-top" alt="...">
          <div class="card-body">
            <h5 class="card-title"> <%= restaurant.getName() %> </h5>
            <p class="card-text">Direccion del restaurante: <%= restaurant.getAdress() %></p>
          </div>
          <ul class="list-group list-group-flush">
          <%
          DishDao dishDao = new DishDao(db.getConnection());
          try {
            ArrayList<Dish> dishes = dishDao.findbyRestaurant(restaurantName);
            for (Dish dish : dishes) {
          %>
          <li class="list-group-item">Nombre del plato: <a href="dish.jsp?name=<%= dish.getName() %>"><%= dish.getName()%></a>, Precio del plato: <%= dish.getPrice()%> € </li>
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
          <div class="card-body">
            <a href="index.jsp" class="card-link">Volver a inicio</a>
          </div>
          </div>
        </div>
    </div>
    <%
        } catch (SQLException sqle) {
    %>
        <div class='alert alert-danger' role='alert'>No se ha podido comunicar correctamente</div>
    <%
            } catch (RestaurantNotFoundException rnfe) {
        %>
            <div class='alert alert-danger' role='alert'>No se ha encontrado el restaurante</div>
        <%
            }
        %>
</body>
</html>
