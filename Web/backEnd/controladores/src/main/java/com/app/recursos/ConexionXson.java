package com.app.recursos;

import java.io.Reader;
import java.io.StringReader;

import com.app.gramaticas.xson.LexerXson;
import com.app.gramaticas.xson.parserXson;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class ConexionXson {

    private MetodoUsuario metodoUsuario = new MetodoUsuario();
    private MetodoTrivia metodoTrivia = new MetodoTrivia();
    private MetodoComponente metodoComponente = new MetodoComponente();
    Multimap<String, String> datos = ArrayListMultimap.create();


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

            return result;

        } catch (Exception e) {
            System.out.println("Error al analizar el texto");
            e.printStackTrace();
        }

        return "Error con el xson";
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

            // Iterar sobre las entradas (clave-valor)
            for (String clave : parserXson.getNuevoUsuario().keySet()) {
                System.out.println("Clave: " + clave);
                // Obtener todos los valores asociados a la clave
                for (String valor : parserXson.getNuevoUsuario().get(clave)) {
                    System.out.println("  Valor: " + valor);
                }
            }

            if (!parserXson.getNuevoUsuario().isEmpty()) {
                System.out.println("Hay datos de usuario---->");

                metodoUsuario.parametros(parserXson.getNuevoUsuario());
                metodoUsuario.analizarParametros();
                metodoUsuario.analizarDatosNew();

            }

            for (String clave : parserXson.getModUsuario().keySet()) {
                System.out.println("Clave: " + clave);
                // Obtener todos los valores asociados a la clave
                for (String valor : parserXson.getModUsuario().get(clave)) {
                    System.out.println("  Valor: " + valor);
                }
            }

            // verficar si es un modificar usuario
            if (!parserXson.getModUsuario().isEmpty()) {

                System.out.println("Hay datos de usuario a modificar---->");

                metodoUsuario.parametros(parserXson.getModUsuario());
                metodoUsuario.analizarParametros();
                metodoUsuario.analizarDatosMod();
             
            }

            for (String clave : parserXson.getEliminarUsuario().keySet()) {
                System.out.println("Clave: " + clave);
                // Obtener todos los valores asociados a la clave
                for (String valor : parserXson.getEliminarUsuario().get(clave)) {
                    System.out.println("  Valor: " + valor);
                }
            }

            // verficar si es un eliminar usuario
            if (!parserXson.getEliminarUsuario().isEmpty()) {

                System.out.println("Hay datos de usuario a eliminar  ----->");
                metodoUsuario.parametros(parserXson.getEliminarUsuario());
                metodoUsuario.analizarParametros();
                metodoUsuario.analizarDatosDelete();
            }


            for (String clave : parserXson.getNuevaTrivia().keySet()) {
                System.out.println("Clave: " + clave);
                // Obtener todos los valores asociados a la clave
                for (String valor : parserXson.getNuevaTrivia().get(clave)) {
                    System.out.println("  Valor: " + valor);
                }
            }


            // verficar si es una nueva trivia

            if (!parserXson.getNuevaTrivia().isEmpty()) {

                System.out.println("Hay datos de nueva trivia---->");

                metodoTrivia.parametros(parserXson.getNuevaTrivia());
                metodoTrivia.analizarParametros();
                metodoTrivia.crearTrivia();
            }

            for (String clave : parserXson.getModificarTrivia().keySet()) {
                System.out.println("Clave: " + clave);
                // Obtener todos los valores asociados a la clave
                for (String valor : parserXson.getModificarTrivia().get(clave)) {
                    System.out.println("  Valor: " + valor);
                }
            }

            // verficar si es una modificar trivia

            if (!parserXson.getModificarTrivia().isEmpty()) {

                System.out.println("Hay datos de modificar trivia---->");

                metodoTrivia.parametros(parserXson.getModificarTrivia());
                metodoTrivia.analizarParametros();
                metodoTrivia.modificarTrivia();
            }

            for (String clave : parserXson.getEliminarTrivia().keySet()) {
                System.out.println("Clave: " + clave);
                // Obtener todos los valores asociados a la clave
                for (String valor : parserXson.getEliminarTrivia().get(clave)) {
                    System.out.println("  Valor: " + valor);
                }
            }

            // verficar si es una eliminar trivia

            if (!parserXson.getEliminarTrivia().isEmpty()) {

                System.out.println("Hay datos de eliminar trivia---->");

                metodoTrivia.parametros(parserXson.getEliminarTrivia());
                metodoTrivia.analizarParametros();
                metodoTrivia.eliminarTrivia();
            }

            for (String clave : parserXson.getAgregarComponente().keySet()) {
                System.out.println("Clave: " + clave);
                // Obtener todos los valores asociados a la clave
                for (String valor : parserXson.getAgregarComponente().get(clave)) {
                    System.out.println("  Valor: " + valor);
                }
            }

            // verficar si es una agregar componente

            if (!parserXson.getAgregarComponente().isEmpty()) {

                System.out.println("Hay datos de agregar componente---->");

               // metodoUsuario.parametros(parserXson.getAgregarComponente());
               // metodoUsuario.analizarParametros();
               // metodoUsuario.analizarDatosAddComponente();

            }


            for (String clave : parserXson.getModificarComponente().keySet()) {
                System.out.println("Clave: " + clave);
                // Obtener todos los valores asociados a la clave
                for (String valor : parserXson.getModificarComponente().get(clave)) {
                    System.out.println("  Valor: " + valor);
                }
            }

            // verficar si es una modificar componente

            if (!parserXson.getModificarComponente().isEmpty()) {

                System.out.println("Hay datos de modificar componente---->");

               // metodoUsuario.parametros(parserXson.getModificarComponente());
               // metodoUsuario.analizarParametros();
               // metodoUsuario.analizarDatosModComponente();
            }

            for (String clave : parserXson.getEliminarComponente().keySet()) {
                System.out.println("Clave: " + clave);
                // Obtener todos los valores asociados a la clave
                for (String valor : parserXson.getEliminarComponente().get(clave)) {
                    System.out.println("  Valor: " + valor);
                }
            }

            // verficar si es una eliminar componente

            if (!parserXson.getEliminarComponente().isEmpty()) {

                System.out.println("Hay datos de eliminar componente---->");

               // metodoUsuario.parametros(parserXson.getEliminarComponente());
               // metodoUsuario.analizarParametros();
               // metodoUsuario.analizarDatosDeleteComponente();
            }





        } catch (Exception e) {
            System.out.println("Error al analizar el texto");
        }
    }

}
