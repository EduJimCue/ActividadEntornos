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
    String textButton = "";
    String textTitle = "";
    String dishName = request.getParameter("name");
     Dish dish = null;
    if (dishName != null) {
        textButton = "Modificar";
        textTitle = "Nuevos datos del plato a modificar";
        Database db = new Database();
        DishDao dishDao = new DishDao(db.getConnection());
        try {
            dish = dishDao.findByDishName(dishName);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }catch (DishNotFoundException dnfe) {
                     dnfe.printStackTrace();
                 }
    } else {
        textButton = "Registrar";
        textTitle = "Nuevo plato a registrar";
    }
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
                $.post("add-dish", formValue, function(data) {
                    $("#result").html(data);
                });
            });
        });
    </script>
    <div class="container">
        <h2><%= textTitle %></h2>
        <form>
            <div class="mb-2">
                <label for="name" class="form-label">Nombre</label>
                <input name="name" type="text" class="form-control w-25" id="name"value="<% if (dish != null) out.print(dish.getName());%>">
            </div>
            <div class="mb-3">
                <label for="price" class="form-label">Precio</label>
                <input name="price" type="number" class="form-control w-25" id="price" step="0.01" value="<% if (dish != null) out.print(dish.getPrice());%>">
            </div>
            <div class="mb-2">
                <label for="restaurant" class="form-label">Restaurante que lo sirve</label>
                <input name="restaurant" type="text" class="form-control w-25" id="restaurant" value="<% if (dish != null) out.print(dish.getRestaurantName());%>">
            </div>
            <input type="hidden" name="action" value="<% if (dish != null) out.print("modify"); else out.print("register"); %>">
            <input type="hidden" name="dishName" value="<% if (dish != null) out.print(dish.getName()); %>">
            <input type="hidden" name="oldRestaurant" value="<% if (dish != null) out.print(dish.getRestaurantName()); %>">
            <button type="submit" class="btn btn-primary"><%= textButton %></button>
        </form>
        <div id="result"></div>
        <p><a href="index.jsp">Vuelve al inicio</a></p>
    </div>
</body>
</html>