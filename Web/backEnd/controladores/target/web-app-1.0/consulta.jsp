<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Realizar Consulta</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
     <link rel="stylesheet" href="css/welcome.css">
</head>
<body>


    <div id="solicitud" >
        <h3>Realizar Consultas</h3>
        <form action="consulta" method="GET">
            <div class="code-container-container">
                <div class="code-container">
                    <div id="lineNumbers" class="line-numbers-container">
                        <pre id="lineNumbersContent" class="line-numbers">1</pre>
                    </div>
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
                    <button class="btn btn-primary" type="submit">Realizar consulta</button>
                </div>
            </div>
        </form>
    </div>


    <!-- Offcanvas con manejador de arrastre -->
    <div class="offcanvas offcanvas-end" tabindex="-1" id="demo" aria-labelledby="offcanvasDemoLabel" style="height: 400px;">
        <div class="offcanvas-header">
            <h5 id="offcanvasDemoLabel">Errores Léxicos y Sintácticos</h5>
            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">
            <!-- Contenido dinámico que se expande -->
            <ul id="listaErrores" class="list-group">
                <!-- Aquí se insertarán errores -->
            </ul>
        </div>
    
        <!-- Agregamos el drag handle -->
        <div class="drag-handle" style="cursor: ns-resize; background-color: #ccc; height: 10px;"></div>
    </div>
    



    <script src="js/welcome.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
