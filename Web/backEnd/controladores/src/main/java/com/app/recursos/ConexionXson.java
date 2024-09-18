package com.app.recursos;

import java.io.Reader;
import java.io.StringReader;

import com.app.gramaticas.xson.LexerXson;
import com.app.gramaticas.xson.parserXson;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


public class ConexionXson {

    public String h ="";
    private  MetodoUsuario metodoUsuario = new MetodoUsuario();
    Multimap<String, String> datos = ArrayListMultimap.create();
    public String a ="";
    public String lista ="";

    public String analizarLogin(String textoIngresado) {


        System.out.println("Conectando al analizador xson");
        System.out.println("Texto ingresado: " + textoIngresado);

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

            a = metodoUsuario.a;
            h = metodoUsuario.mmm;

            return hoa;



        } catch (Exception e) {
            System.out.println("Error al analizar el texto");
        }

        return "Error con el xson";
    }




    public void analizadorGeneral(String textoIngresado) {
        System.out.println("Conectando al analizador xson");
        System.out.println("Texto ingresado: " + textoIngresado);

        // Conexión al analizador xson

        try {
            
            Reader reader = new StringReader(textoIngresado);
            LexerXson lexer = new LexerXson(reader);
            parserXson parserXson = new parserXson(lexer);
            parserXson.parse();

            // se conecto con el el metodo Usuario
          

    
        } catch (Exception e) {
            System.out.println("Error al analizar el texto");
        }
    }

}
