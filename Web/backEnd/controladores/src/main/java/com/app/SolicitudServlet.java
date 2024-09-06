package com.app;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/solicitud")
public class SolicitudServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Leer el texto enviado desde el formulario
        String textoIngresado = request.getParameter("textoIngresado");
        
        // Verificar si se ha recibido el valor o es null
        if (textoIngresado != null) {
            System.out.println("Texto ingresado: " + textoIngresado);
        } else {
            System.out.println("No se recibió el texto");
        }
        
        // Responder al cliente con alguna acción
        response.setContentType("text/html");
        response.getWriter().println("<h2>Texto recibido correctamente</h2>");
        response.getWriter().println("<p>" + textoIngresado + "</p>");
    }
}
