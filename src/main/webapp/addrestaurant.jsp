<%@ page import="Restaurant.dao.Database" %>
<%@ page import="Restaurant.dao.RestaurantDao" %>
<%@ page import="Restaurant.domain.Restaurant" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page import="Restaurant.domain.User" %>
<%@ page import="Restaurant.exceptions.RestaurantNotFoundException" %>
<%
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null) {
        response.sendRedirect("index.jsp");
    }
    String textButton = "";
    String textTitle = "";
    String restaurantName = request.getParameter("name");
     Restaurant restaurant = null;
    if (restaurantName != null) {
        textButton = "Modificar";
        textTitle = "Nuevos datos del restaurante a modificar";
        Database db = new Database();
        RestaurantDao restaurantDao = new RestaurantDao(db.getConnection());
        try {
            restaurant = restaurantDao.findByName(restaurantName);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }catch (RestaurantNotFoundException rnfe) {
                     rnfe.printStackTrace();
                 }
    } else {
        textButton = "Registrar";
        textTitle = "Nuevo restaurante a registrar";
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
                $.post("add-restaurant", formValue, function(data) {
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
                <input name="name" type="text" class="form-control w-25" id="name"value="<% if (restaurant != null) out.print(restaurant.getName());%>">
            </div>
            <div class="mb-3">
                <label for="postalCode" class="form-label">Codigo Postal</label>
                <input name="postalCode" type="number" class="form-control w-25" id="postalCode" value="<% if (restaurant != null) out.print(restaurant.getPostal_code());%>">
            </div>
            <div class="mb-2">
                <label for="adress" class="form-label">Direccion</label>
                <input name="adress" type="text" class="form-control w-25" id="adress"value="<% if (restaurant != null) out.print(restaurant.getAdress());%>">
            </div>
            <input type="hidden" name="action" value="<% if (restaurant != null) out.print("modify"); else out.print("register"); %>">
            <input type="hidden" name="restaurantName" value="<% if (restaurant != null) out.print(restaurant.getName()); %>">
            <button type="submit" class="btn btn-primary"><%= textButton %></button>
        </form>
        <div id="result"></div>
        <p><a href="index.jsp">Vuelve al inicio</a></p>
    </div>
</body>
</html>