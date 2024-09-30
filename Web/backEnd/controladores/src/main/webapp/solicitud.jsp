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


<div id="solicitud">
    <h3>Realizar Solicitud</h3>
    <form id="consultaForm">
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
                <button id="realizarConsultaBtn" class="btn btn-primary" type="button">Realizar Solicitud</button>
            </div>
        </div>
             
     

    </form>
</div>



    <!-- Offcanvas con manejador de arrastre -->
    <!-- Offcanvas HTML -->
    <div class="offcanvas offcanvas-bottom" id="demo" tabindex="-1" aria-labelledby="offcanvasBottomLabel">
        <div class="offcanvas-header">
            <h5 id="offcanvasBottomLabel">Errores Detectados</h5>
            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">
            <!-- Aquí se mostrará la tabla con los errores -->
            <table class="table table-striped" id="erroresTable">
                <thead>
                    <tr>
                        <th>Tipo</th>
                        <th>Lexema</th>
                        <th>Descripción</th>
                        <th>Línea</th>
                        <th>Columna</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Filas de errores se agregarán dinámicamente aquí -->
                </tbody>
            </table>
        </div>
    </div>

        <!-- Offcanvas para mensajes -->
    <div class="offcanvas offcanvas-bottom" id="mensajesCanvas" tabindex="-1" aria-labelledby="offcanvasMensajesLabel">
        <div class="offcanvas-header">
            <h5 id="offcanvasMensajesLabel">Mensajes Generados</h5>
            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">
            <ul id="mensajesList" class="list-group">
                <!-- Aquí se agregarán los mensajes dinámicamente -->
            </ul>
        </div>
    </div>





    <script src="js/welcome.js"></script>
    <script src="js/solicitud.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
