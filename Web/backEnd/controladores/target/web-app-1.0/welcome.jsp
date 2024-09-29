<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">


<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bienvenido</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/welcome.css">
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
                        <a class="nav-link" href="?seccion=solicitud">Realizar solicitudes</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="?seccion=consulta">Realizar consultas</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="?seccion=verTrivia">Ver trivias</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="?seccion=export">Exportar trivias</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="?seccion=import">Importar trivias</a>
                    </li>
                </ul>
                <form class="d-flex" action="LogoutServlet" method="post">
                    <button class="btn btn-outline-danger" type="submit">Cerrar Sesión</button>
                </form>
            </div>
        </div>
    </nav>

    <!-- Contenido de la página -->
    <div class="container">
        <div class="text-center mt-5">
            <!-- Mostrar el nombre del usuario -->
            <h1>Bienvenido, 
                <% 
                    String nombreUsuario = (String) session.getAttribute("nombreUsuario");
                    if (nombreUsuario != null) {
                        out.print(nombreUsuario); 
                    } else {
                        out.print("Invitado");
                    }
                %>!
            </h1>
        </div>

        <div id="contenido-dinamico" class="mt-5">

            <div id="contenido-dinamico" class="mt-5">
                <%
                    String seccion = request.getParameter("seccion");
                    if (seccion != null) {
                        if (seccion.equals("solicitud")) {
                            %><jsp:include page="solicitud.jsp" /> <%
                        } else if (seccion.equals("consulta")) {
                            %><jsp:include page="consulta.jsp" /> <%
                        } else if (seccion.equals("verTrivia")) {
                            %><jsp:include page="verTrivias.jsp" /> <%
                        }else if (seccion.equals("export")) {
                            %><jsp:include page="export.jsp" /> <%
                        }else if (seccion.equals("import")) {
                            %><jsp:include page="import.jsp" /> <%
                        }
                    }
                %>
            </div>

            <p>
            <p>
            </p>
            </p>

        </div>

    </div>

    <script src="js/welcome.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
