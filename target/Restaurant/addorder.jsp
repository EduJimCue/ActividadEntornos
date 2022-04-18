<%@ page import="Restaurant.dao.Database" %>
<%@ page import="Restaurant.dao.RestaurantDao" %>
<%@ page import="Restaurant.domain.Restaurant" %>
<%@ page import="Restaurant.dao.DishDao" %>
<%@ page import="Restaurant.domain.Dish" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page import="Restaurant.domain.User" %>
<%@ page import="Restaurant.exceptions.DishNotFoundException" %>
<%
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null) {
        response.sendRedirect("index.jsp");
    }

    String dish = request.getParameter("dish");

    String restaurant = request.getParameter("restaurant");
%>


<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
    <script type="text/javascript">
        $(document).ready(function() {
            $("form").on("submit", function(event) {
                event.preventDefault();
                var formValue = $(this).serialize();
                $.post("add-order", formValue, function(data) {
                    $("#result").html(data);
                });
            });
        });
    </script>
    <div class="container">
        <h2>Nuevo pedido</h2>
        <form>
            <div class="mb-2">
                <label for="dish" class="form-label">Nombre del plato</label>
                <input name="dish" type="text" class="form-control w-25" id="dish"  value="<% if (dish != null) out.print(dish);%>">
            </div>
            <div class="mb-2">
                <label for="restaurant" class="form-label">Restaurante que lo sirve</label>
                <input name="restaurant" type="text" class="form-control w-25" id="restaurant"  value="<% if (restaurant != null) out.print(restaurant);%>">
            </div>
            <button type="submit" class="btn btn-primary">Hacer pedido</button>
        </form>
        <div id="result"></div>
        <p><a href="index.jsp">Vuelve al inicio</a></p>
    </div>
</body>
</html>