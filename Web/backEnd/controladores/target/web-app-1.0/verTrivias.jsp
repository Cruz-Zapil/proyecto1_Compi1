<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Ver Trivias</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h3>Trivias Disponibles para 
            <% 
                String nombreUsuario = (String) session.getAttribute("nombreUsuario");
                out.print(nombreUsuario != null ? nombreUsuario : "Usuario"); 
            %>
        </h3>

        <div id="trivias-container">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Tiempo</th>
                        <th>Usuario</th>
                        <th>Tema</th>
                        <th>Fecha de Creación</th>
                    </tr>
                </thead>
                <tbody id="triviaTableBody">
                    <!-- Aquí se llenará dinámicamente la tabla -->
                </tbody>
            </table>
        </div>


      
    </div>

    <script src="js/verTrivia.js"></script>

</body>
</html>
