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
    String restaurantName = request.getParameter("name");
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
                $.post("delete-restaurant", formValue, function(data) {
                    $("#result").html(data);
                });
            });
        });
    </script>
    <div class="container">
        <h2>Seguro que quieres eliminar el restaurante <%= restaurantName %>?</h2>
        <h2>Ten en cuenta que no se pueden eliminar restauntes que tengan platos</h2>
        <form>

            <input type="hidden" name="restaurantName" value="<%=restaurantName%>">
            <button type="submit" class="btn btn-primary">Eliminar</button>
        </form>
        <div id="result"></div>
        <p><a href="index.jsp">Vuelve al inicio</a></p>
    </div>
</body>
</html>