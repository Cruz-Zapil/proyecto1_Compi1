<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-4">
                <h2 class="text-center my-4">Iniciar Sesión</h2>
                
                <form action="LoginServlet" method="post" class="border p-4 bg-light shadow rounded">
                    <div class="mb-3">
                        <label for="username" class="form-label">Usuario:</label>
                        <input type="text" id="username" name="username" class="form-control" placeholder="Ingresa tu usuario" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Contraseña:</label>
                        <input type="password" id="password" name="password" class="form-control" placeholder="Ingresa tu contraseña" required>
                    </div>
                    <div class="d-grid">
                        <input type="submit" class="btn btn-primary" value="Ingresar">
                    </div>
                </form>

                <!-- Mostrar mensaje de error si existe -->
                <%
                    String message = request.getParameter("message");
                    if (message != null) {
                %>
                <div class="alert alert-danger mt-3" role="alert">
                    <%= message %>
                </div>
                <%
                    }
                %>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
