package com.app.recursos;

import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

import com.app.gramaticas.xson.LexerXson;
import com.app.gramaticas.xson.parserXson;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class ConexionXson {

    private MetodoUsuario metodoUsuario = new MetodoUsuario();
    private MetodoTrivia metodoTrivia = new MetodoTrivia();
    private MetodoComponente metodoComponente = new MetodoComponente();
    Multimap<String, String> datos = ArrayListMultimap.create();
    private String nombreUsuario;
    private String idUser;
    public String mensaje ="";



    public String analizarLogin(String textoIngresado) {

        // Conexión al analizador xson

        try {

            Reader reader = new StringReader(textoIngresado);
            LexerXson lexer = new LexerXson(reader);
            parserXson parserXson = new parserXson(lexer);
            parserXson.parse();

            // se conecto con el el metodo Usuario

            // obtener datos de clave valor
            datos = parserXson.getLoginUsuario();

            /// enviar los datos de clave valor para analizar

            String result = metodoUsuario.loginUsuario(datos);
            nombreUsuario = metodoUsuario.getNombreUsu();
            mensaje = metodoUsuario.getRespuesta();
            idUser = metodoUsuario.getIdUser();


            return result;

        } catch (Exception e) {
            System.out.println("Error al analizar el texto");
            e.printStackTrace();
        }

        return "Error con el xson";
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getIdUser() {
        return idUser;
    }

    public void analizadorGeneral(String textoIngresado) {

        // Conexión al analizador xson

        try {

            Reader reader = new StringReader(textoIngresado);
            LexerXson lexer = new LexerXson(reader);
            parserXson parserXson = new parserXson(lexer);
            parserXson.parse();

            // se conecto con el el metodo Usuario
            // verficar si es un nuevo usuario

   
            /* 


            if (!parserXson.getNuevoUsuario().isEmpty()) {
                System.out.println("Hay datos de usuario---->");

                metodoUsuario.parametros(parserXson.getNuevoUsuario());
                metodoUsuario.analizarParametros();
                metodoUsuario.analizarDatosNew();

            }

   

            // verficar si es un modificar usuario
            if (!parserXson.getModUsuario().isEmpty()) {

                System.out.println("Hay datos de usuario a modificar---->");

                metodoUsuario.parametros(parserXson.getModUsuario());
                metodoUsuario.analizarParametros();
                metodoUsuario.analizarDatosMod();
             
            }

        
            // verficar si es un eliminar usuario
            if (!parserXson.getEliminarUsuario().isEmpty()) {

                System.out.println("Hay datos de usuario a eliminar  ----->");
                metodoUsuario.parametros(parserXson.getEliminarUsuario());
                metodoUsuario.analizarParametros();
                metodoUsuario.analizarDatosDelete();
            }


            // verficar si es una nueva trivia

            if (!parserXson.getNuevaTrivia().isEmpty()) {

                System.out.println("Hay datos de nueva trivia---->");

                metodoTrivia.parametros(parserXson.getNuevaTrivia());
                metodoTrivia.crearTrivia();
            }

   
            // verficar si es una modificar trivia

            if (!parserXson.getModificarTrivia().isEmpty()) {

                System.out.println("Hay datos de modificar trivia---->");

                metodoTrivia.parametros(parserXson.getModificarTrivia());
                metodoTrivia.modificarTrivia();
            }

            // verficar si es una eliminar trivia

            if (!parserXson.getEliminarTrivia().isEmpty()) {

                System.out.println("Hay datos de eliminar trivia---->");

                metodoTrivia.parametros(parserXson.getEliminarTrivia());
                metodoTrivia.eliminarTrivia();
            }
*/
     

            // verficar si es una agregar componente

            if (!parserXson.getAgregarComponente().isEmpty()) {

                System.out.println("Hay datos de agregar componente---->");

                metodoComponente.parametros(parserXson.getAgregarComponente());

                metodoComponente.crearComponente();

            }


       

            // verficar si es una modificar componente

            if (!parserXson.getModificarComponente().isEmpty()) {

                System.out.println("Hay datos de modificar componente---->");

                metodoComponente.parametros(parserXson.getModificarComponente());
                metodoComponente.modificarComponente();
             
            }



            // verficar si es una eliminar componente

            if (!parserXson.getEliminarComponente().isEmpty()) {

                System.out.println("Hay datos de eliminar componente---->");
                metodoComponente.parametros(parserXson.getEliminarComponente());
                metodoComponente.eliminarComponente();

            }


        } catch (Exception e) {
            System.out.println("Error al analizar el texto");
        }
    }

}
