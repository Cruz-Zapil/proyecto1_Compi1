<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Estilos personalizados -->
    <link rel="stylesheet" href="css/index.css">
</head>
<body>

    <!-- Barra de navegación -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">App-Web</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <!-- Corregido el enlace al servlet -->
                        <a class="nav-link active" aria-current="page" href="actualizarDatos">Actualizar datos</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Contenido principal -->
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6 custom-login-box">
                <h2 class="text-center my-4">Iniciar Sesión</h2>
                
                <!-- Formulario de login -->
                <form action="LoginServlet" method="post" class="border p-4 bg-light shadow rounded" onsubmit="encodeTextareaContent()">
                    <div class="mb-3">
                        <label for="codeArea" class="form-label">Ingrese su código:</label>
                        <div class="code-container-container">
                            <div class="code-container">
                                <div id="lineNumbers" class="line-numbers-container">
                                    <pre id="lineNumbersContent" class="line-numbers">1</pre>
                                </div>
                                <textarea id="codeArea" name="textoIngresado" class="form-control" rows="10"
                                          oninput="updateLineNumbers()" onscroll="syncScroll()" 
                                          onkeyup="updateCursorPosition(event)" onclick="updateCursorPosition(event)">
                                </textarea>
                            </div>
                        </div>
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

    <!-- Validación del formulario con JavaScript -->
    <script>

    // Función para codificar el contenido del textarea
    function encodeTextareaContent() {
        var textarea = document.getElementById('codeArea');
        textarea.value = encodeURIComponent(textarea.value);
    }


        document.querySelector("form").addEventListener("submit", function(event) {
            const codeArea = document.getElementById("codeArea").value.trim();
            if (codeArea === "") {
                alert("Por favor, ingrese algún texto.");
                event.preventDefault(); // Evita el envío del formulario
            }
        });
    </script>

</body>
</html>
