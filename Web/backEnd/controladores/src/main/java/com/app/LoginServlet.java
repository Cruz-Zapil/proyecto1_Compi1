package com.app;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.app.recursos.ConexionXson;

@WebServlet("/LoginServlet")

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Leer el texto enviado desde el formulario
        String textoIngresado = request.getParameter("textoIngresado");
     
        // Verificar si se ha recibido el valor o es null
        if (!textoIngresado.isEmpty()) {
            
            try {
                // usar el analizador Xson
                ConexionXson conexionXson = new ConexionXson();
                String resultado = conexionXson.analizarLogin(textoIngresado);

                if ("Usuario logeado".equals(resultado)) {
                    // Redirigir a welcome.jsp
                    response.sendRedirect("welcome.jsp");
                } else {
                    // Si el usuario no está autenticado, redirige de nuevo al formulario con un mensaje
                    response.sendRedirect("index.jsp?message=Usuario no autenticado" +"   " + textoIngresado);
                   
                }

               
                response.setContentType("text/html");
                response.getWriter().println("<h2>Texto ingresado: " + textoIngresado + "</h2>");
                response.getWriter().println("<h2>Resultado del análisis: " + resultado + "</h2>");
                 // No escribas más contenido después de sendRedirect
                 return;

            } catch (Exception e) {

                e.printStackTrace();
                response.getWriter().println("Error al conectar al xson:  " + e.getMessage()+  "    " + textoIngresado);
            }

        } else {
            // Respuesta al cliente indicando que no se recibió el texto
            response.setContentType("text/html");
            response.getWriter().println("<h2>No se recibió el texto ingresado.</h2>");
        }

    }
}
