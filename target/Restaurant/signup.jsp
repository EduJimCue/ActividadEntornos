<%@ page import="Restaurant.dao.Database" %>
<%@ page import="Restaurant.dao.RestaurantDao" %>
<%@ page import="Restaurant.domain.Restaurant" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page import="Restaurant.domain.User" %>
<%@ page import="Restaurant.dao.UserDao" %>
<%@ page import="Restaurant.exceptions.RestaurantNotFoundException" %>
<%
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null) {
            response.sendRedirect("index.jsp");
        }

    String textButton = "";
    String textTitle = "";
    if (currentUser != null) {
        textButton = "Modificar";
        textTitle = "Nuevos datos del usuario a modificar";

    } else {
        textButton = "Registrar";
        textTitle = "Nuevo usuario a registrar";
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
                $.post("add-user", formValue, function(data) {
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
                <input name="name" type="text" class="form-control w-25" id="name" value="<% out.print(currentUser.getName());%>">
            </div>
            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input name="username" type="text" class="form-control w-25" id="username" value="<% out.print(currentUser.getUsername());%>">
            </div>
            <div class="mb-2">
                <label for="password" class="form-label">Password</label>
                <input name="password" type="password" class="form-control w-25" id="password" value="<% out.print(currentUser.getPassword());%>">
            </div>
            <input type="hidden" name="action" value="<% if (currentUser != null) out.print("modify"); else out.print("register"); %>">
            <input type="hidden" name="UserName" value="<% if (currentUser != null) out.print(currentUser.getUsername()); %>">
            <button type="submit" class="btn btn-primary"><%= textButton %></button>
        </form>
        <div id="result"></div>
        <p><a href="index.jsp">Vuelve al inicio</a></p>
    </div>
</body>
</html>