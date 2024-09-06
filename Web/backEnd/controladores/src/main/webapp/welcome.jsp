<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bienvenido</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Estilos personalizados -->
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
                        <a class="nav-link active" aria-current="page" href="#solicitud" onclick="mostrarSeccion('solicitud')">Realizar solicitudes</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#demo">Realizar consultas</a>
                    </li>     
                    <li class="nav-item">
                        <a class="nav-link" href="#demo">Ver trivias</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#demo">Exportar trivias</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#demo">Importar trivias</a>
                    </li>
                </ul>
                <!-- Botón de cerrar sesión -->
                <form class="d-flex" action="LogoutServlet" method="post">
                    <button class="btn btn-outline-danger" type="submit">Cerrar Sesión</button>
                </form>
            </div>
        </div>
    </nav>

    <!-- Contenido de la página -->
    <div class="container">
        <div class="text-center mt-5">
            <h1>Bienvenido!!!</h1>
        </div>

       <!-- Sección de Solicitudes (Oculta por defecto) -->
        <div id="solicitud" class="hidden mt-5">
            <h3>Realizar Solicitud</h3>

            <form action="solicitud" method="POST">
                <div class="code-container-container">
                    <div class="code-container">
                        <div id="lineNumbers" class="line-numbers-container">
                            <pre id="lineNumbersContent" class="line-numbers">1</pre>
                        </div>
                        <!-- Añade el atributo 'name' -->
                        <textarea id="codeArea" name="textoIngresado" class="form-control" 
                                  oninput="updateLineNumbers()" onscroll="syncScroll()" 
                                  onkeyup="updateCursorPosition(event)" 
                                  onclick="updateCursorPosition(event)">
                        </textarea>
                    </div>
                </div>

                <div class="row mt-3">
                    <div class="col-md-12">
                        <p id="cursorPosition">Línea: 1, Columna: 1</p>
                    </div>
                </div>

                <div class="row mt-3">
                    <div class="col-md-12">
                        <button class="btn btn-primary" type="submit">Enviar Solicitud</button>
                    </div>
                </div>
            </form>
        </div>


        <!-- Offcanvas Dinámico -->
        <div class="offcanvas offcanvas-bottom" id="demo" data-bs-backdrop="false" data-bs-keyboard="false">
            <!-- Controlador de arrastre -->
            <div class="drag-handle"></div>
            <div class="offcanvas-header">
                <h1 class="offcanvas-title">Offcanvas con Tamaño Dinámico</h1>
                <button type="button" class="btn-close" data-bs-dismiss="offcanvas"></button>
            </div>
            <div class="offcanvas-body">
                <p>Puedes arrastrar este offcanvas hacia arriba para expandirlo.</p>
            </div>
        </div>
            
    </div>




    <!-- scripts -->
    <script src="js/welcome.js"></script>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
