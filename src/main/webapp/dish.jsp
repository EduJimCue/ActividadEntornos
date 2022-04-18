<%@ page import="Restaurant.dao.Database" %>
<%@ page import="Restaurant.dao.RestaurantDao" %>
<%@ page import="Restaurant.domain.Restaurant" %>
<%@ page import="Restaurant.dao.DishDao" %>
<%@ page import="Restaurant.domain.Dish" %>
<%@ page import= "Restaurant.exceptions.RestaurantNotFoundException" %>
<%@ page import= "Restaurant.exceptions.DishNotFoundException" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Restaurant.domain.User" %>
<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<%
    User currentUser = (User) session.getAttribute("currentUser");
%>

<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<script type="text/javascript">
        $(document).ready(function() {
            $("card-link").on("submit", function(event) {
                event.preventDefault();
                var formValue = $(this).serialize();
                $.post("delete-dish", formValue, function(data) {
                    $("#result").html(data);
                });
            });
        });
    </script>
    <%
        String dishName = request.getParameter("name");
        Database db = new Database();
        DishDao dishDao = new DishDao(db.getConnection());

        try {
            Dish dish = dishDao.findByDishName(dishName);
    %>
    <div class="container">
    <div class="card text-center">
        <div class="card" style="width: 30rem; margin-right:auto; margin-left:auto; ">
          <img src="..." class="card-img-top" alt="...">
          <div class="card-body">
            <h5 class="card-title"> <%= dish.getName() %> </h5>
            <p class="card-text">Precio del plato: <%= dish.getPrice() %>€</p>
          </div>
          <ul class="list-group list-group-flush">
            <li class="list-group-item">Descripcion:</li>
            <li class="list-group-item">Ingredientes:</li>
            <li class="list-group-item">Restaurante que lo sirve:<a href="restaurant.jsp?name=<%= dish.getRestaurantName() %>"><%= dish.getRestaurantName() %></a></li>
          </ul>
          <div class="card-body">
            <a href="index.jsp" class="card-link">Volver a inicio</a>
            <%
               if (currentUser != null) {
            %>
            <a href="addorder.jsp?restaurant=<%= dish.getRestaurantName()%>&dish=<%=dish.getName()%>" class="card-link">Realizar pedido</a>
            <%
                }
            %>
          </div>
          </div>
        </div>
    </div>
    <%
        } catch (SQLException sqle) {
    %>
        <div class='alert alert-danger' role='alert'>No se ha podido comunicar correctamente</div>
    <%
            } catch (DishNotFoundException dnfe) {
        %>
            <div class='alert alert-danger' role='alert'>No se ha encontrado el plato</div>
        <%
            }
        %>
</body>
</html>
