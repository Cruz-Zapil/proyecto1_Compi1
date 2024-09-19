package com.app.recursos;

import java.io.Reader;
import java.io.StringReader;

import com.app.gramaticas.xson.LexerXson;
import com.app.gramaticas.xson.parserXson;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class ConexionXson {

    private MetodoUsuario metodoUsuario = new MetodoUsuario();
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

            String hoa = metodoUsuario.loginUsuario(datos);

            return hoa;

        } catch (Exception e) {
            System.out.println("Error al analizar el texto");
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

        } catch (Exception e) {
            System.out.println("Error al analizar el texto");
        }
    }

}
