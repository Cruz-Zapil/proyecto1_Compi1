package com.app;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.gramaticas.Errorx;
import com.app.gramaticas.ReporteError;
import com.app.recursos.ConexionXson;
import com.google.gson.Gson;


@WebServlet("/solicitud")
public class SolicitudServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Leer el texto enviado desde el formulario
        String textoIngresado = request.getParameter("textoIngresado");
        
        /// lista para almacenar erorres 
        List<Errorx> listaErrores = new ArrayList<>();
        String mensaje="hola muhndo ";

        // Verificar si se ha recibido el valor o es null
        if (textoIngresado != null) {
            
            try {
                
                // Procesar el texto recibido
                ConexionXson conexionXson = new ConexionXson();
                conexionXson.analizadorGeneral(textoIngresado);
                listaErrores =  ReporteError.getListaErrores();
                
                

            } catch (Exception e) {
                
                listaErrores.add(new Errorx("Error", "conexion","Error en la conexion", 0, 0));
                
            }

        } else {
            System.out.println("No se recibió el texto");
        }
        
               // Crear un objeto para enviar como respuesta
        ResponseData responseData = new ResponseData(mensaje, listaErrores);

        // Convertir la respuesta a JSON usando Gson
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(responseData);

        // Configurar la respuesta
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();


    }

    // Clase interna para estructurar la respuesta
    private static class ResponseData {
        private String mensaje;
        private List<Errorx> errores;

        public ResponseData(String mensaje, List<Errorx> errores) {
            this.mensaje = mensaje;
            this.errores = errores;
        }

        public String getMensaje() {
            return mensaje;
        }

        public List<Errorx> getErrores() {
            return errores;
        }
    }




}
            