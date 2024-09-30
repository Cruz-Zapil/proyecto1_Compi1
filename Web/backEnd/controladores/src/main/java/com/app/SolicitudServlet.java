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
import com.app.gramaticas.Reporte;
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
        /// lista para almacenar mensajes
        String mensaje="hola muhndo ";
        mensaje+=textoIngresado;
 
        // Verificar si se ha recibido el valor o es null
        if (textoIngresado != null) {
            
            try {
                Reporte.limpiarErrores();
                Reporte.limpiarMensajes();
                
                // Procesar el texto recibido
                ConexionXson conexionXson = new ConexionXson();
                conexionXson.analizadorGeneral(textoIngresado);
                listaErrores =  Reporte.getListaErrores();
                mensaje += listaErrores.size();
                mensaje += " se esta procesando";

            } catch (Exception e) {
                
                listaErrores.add(new Errorx("Error", "conexion","Error en la conexion", 0, 0));
                
            }

        } else {
            System.out.println("No se recibió el texto");
        }
        
               // Crear un objeto para enviar como respuesta
        ResponseData responseData = new ResponseData(mensaje, listaErrores, Reporte.getListaMensajes());

        // Convertir la respuesta a JSON usando Gson
        Gson gson = new Gson();
        String data = gson.toJson(responseData);

        // Configurar la respuesta
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

       PrintWriter out = response.getWriter();
       out.print(data);
       out.flush();


    }

    // Clase interna para estructurar la respuesta
    private static class ResponseData {
        private String mensaje;
        private List<Errorx> errores;
        private List<String> mensajes;

        public ResponseData(String mensaje, List<Errorx> errores, List<String> mensajes) {

            this.mensaje = mensaje;
            this.errores = errores;
            this.mensajes = mensajes;
        }

        public String getMensaje() {
            return mensaje;
        }

        public List<Errorx> getErrores() {
            return errores;
        }
        
        public List<String> getMensajes() {
            return mensajes;
        }
    }




}
            