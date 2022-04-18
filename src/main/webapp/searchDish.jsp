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
                $.post("search-dish", formValue, function(data) {
                    $("#result").html(data);
                });
            });
        });
    </script>
    <div class="container">
        <h2>Buscador de Platos</h2>
        <form>
            <div class="mb-2">
                <label for="text" class="form-label">Introduce el nombre del plato o del restaurante que lo sirve</label>
                <input name="text" type="text" class="form-control w-25" id="text">
            </div>
            <button type="submit" class="btn btn-primary">Buscar</button>
        </form>
        <div id="result"></div>
        <p><a href="index.jsp">Vuelve al inicio</a></p>
    </div>
</body>
</html>