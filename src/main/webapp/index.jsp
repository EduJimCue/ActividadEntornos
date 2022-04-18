<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="Restaurant.domain.User"
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
        <h2>Bienvenido a las pruebas de la webapp del restaurante</h2>
        <div class="alert alert-success" role="alert">
                  Bienvenido <% if (currentUser != null) out.print(currentUser.getName()); %>
                </div>
        <ul>
        <li><a href="login.jsp">Inicia sesion</a></li>
        <%
            if(currentUser == null)  {
        %>
        <li><a href="signup.jsp">Registra un nuevo usuario</a></li>
        <%
            }
        %>
        <li><a href="contact.jsp">Contacta con nosotros</a></li>
        <li><a href="restaurants.jsp">Lista de Restaurantes</a></li>
        <li><a href="dishes.jsp">Lista de platos</a></li>
        <li><a href="searchRestaurant.jsp">Buscar restaurantes por su nombre o por un plato que sirva</a></li>
        <li><a href="searchDish.jsp">Buscar platos por su nombre o por restaurante que lo sirva</a></li>
         <%
                     if ((currentUser != null) && (currentUser.getRole().equals("admin"))) {
         %>
                     <h3>Apartado de administradores</h3>
                     <li><a href="addrestaurant.jsp">Añadir restaurante</a></li>
                     <li><a href="adddish.jsp">Añadir plato</a></li>
                     <li><a href="allorders.jsp">Todos los pedidos</a></li>
         <%
                     }
         if (currentUser != null) {
            %>
                <h3>Apartado de usuario</h3>
                <li><a href="addorder.jsp">Hacer un pedido</a></li>
                <li><a href="userorders.jsp">Mis pedidos</a></li>
                <li><a href="signup.jsp">Modificar usuario</a></li>
                <li><a href="logout">Cerrar sesión</a></li>
            <%
            }
            %>
        </ul>
    </div>
</body>
</html>
