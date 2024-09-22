package com.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

import com.app.gramaticas.xson.LexerXson;
import com.app.gramaticas.xson.parserXson;
import com.app.recursos.MetodoUsuario;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Leer el texto enviado desde el formulario
        String textoIngresado = request.getParameter("textoIngresado");
        String txtList = java.net.URLDecoder.decode(textoIngresado, "UTF-8");  

        // Configurar la respuesta para que sea HTML
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Verificar si se ha recibido el valor o es null
        if (textoIngresado != null) {
            

            try {
                // usar el analizador Xson
                Reader input = new StringReader(txtList);
                LexerXson lexer = new LexerXson(input);
                parserXson parser = new parserXson(lexer);
                parser.parse();

                // Conexión a la base de datos

                MetodoUsuario metodoUsuario = new MetodoUsuario();
                String resultado = metodoUsuario.loginUsuario(parser.getLoginUsuario());


                if ("Usuario logeado".equals(resultado)) {
                    // Redirigir a welcome.jsp
                    response.sendRedirect("welcome.jsp");
                } else {
                    // Si el usuario no está autenticado, redirige de nuevo al formulario con un mensaje
                    response.sendRedirect("index.jsp?message=Usuario no autenticado"+ txtList +"   " + textoIngresado);
                   
                }

               
                response.setContentType("text/html");
                response.getWriter().println("<h2>Texto ingresado: " + textoIngresado + "</h2>");
                response.getWriter().println("<h2>Resultado del análisis: " + resultado + "</h2>");
                 // No escribas más contenido después de sendRedirect
                 return;

            } catch (Exception e) {

                e.printStackTrace();
                response.getWriter().println("Error al crear el archivo:  " + e.getMessage()+ txtList + "    " + textoIngresado);
            }

        } else {
            // Respuesta al cliente indicando que no se recibió el texto
            response.setContentType("text/html");
            response.getWriter().println("<h2>No se recibió el texto ingresado.</h2>");
        }

    }
}
