package com.app.recursos;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import com.app.gramaticas.db.LexerDB;
import com.app.gramaticas.db.parserDB;
import com.app.recursosdb.Usuario;
import com.app.recursosdb.trivia.Componente;
import com.app.recursosdb.trivia.Trivia;

public class ConexionDB {

    private ArrayList<Usuario> listaUsuario = new ArrayList<Usuario>();
    private ArrayList<Trivia> listaTriva = new ArrayList<Trivia>();
    private ArrayList<Componente> listaRegistro = new ArrayList<Componente>();
   // public ArrayList<> listaRegistro = new ArrayList<Usuario>();

    /// recopilar los string de los archivos almacenados en la compu
    private String usuario = "";
    private String registro = "";
    private String trivia = "";
    private String mensaj_Prueba = "";

    public void recopilarArchivos() {

        // Usar File.separator para manejar el separador de archivos correcto según el
        // SO
        File carpeta = new File("/webApp/");

        // Listar todos los archivos que terminan en ".db"
        File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".db"));

        if (archivos != null) {
            for (File archivo : archivos) {
                try {
                    // Leer todas las líneas del archivo
                    List<String> lineas = Files.readAllLines(Paths.get(archivo.getAbsolutePath()));

                    // Determinar el tipo de archivo por el nombre y almacenar el contenido en la
                    // variable correspondiente
                    if (archivo.getName().equals("registro.db")) {
                        registro = String.join("\n", lineas);
                    } else if (archivo.getName().equals("usuario.db")) {
                        usuario = String.join("\n", lineas);
                    } else if (archivo.getName().equals("trivia.db")) {
                        trivia = String.join("\n", lineas);
                    } 

                } catch (IOException e) {
                    System.out.println("Error al leer el archivo: " + archivo.getName());
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("No se encontraron archivos en la carpeta.");
        }
    }

    public void analizarRegistro() {
        if (!registro.isEmpty()) {
            // analizar
            analizadorDB(registro);
            mensaj_Prueba += "Se encontró el archivo de registro y fue analizado.\n";

        } else {
            mensaj_Prueba = " No se encontró el archivo de registro.\n ";
            System.out.println("No se encontró el archivo de registro.");

        }

    }


    public void analizarUsuario() {

        if (!usuario.isEmpty()) {
            // analizar
            analizadorDB(usuario);

        } else {
            mensaj_Prueba += "No se encontró el archivo de usuario.\n";
            System.out.println("No se encontró el archivo de usuario.");
        }

    }

    public void analizarTrivia() {

        if (!trivia.isEmpty()) {
            // analizar
            analizadorDB(trivia);
            mensaj_Prueba += "Se encontró el archivo de trivia y fue analizado.\n";

        } else {
            mensaj_Prueba += "No se encontró el archivo de trivia.\n";
            System.out.println("No se encontró el archivo de trivia.");
        }

    }

    public void analizadorDB(String contendio) {

        try {

            System.out.println("\n\n\n"+contendio+"\n\n\n" );


            Reader reader = new StringReader(contendio);
            LexerDB lexer = new LexerDB(reader);
            parserDB parser = new parserDB(lexer);
            parser.parse();

            /// obtener todo los registros de la base de datos

            listaUsuario = parser.getUsuarios();
            listaTriva = parser.getTrivias();


           // listaRegistro = parser.getComponentes();

        } catch (Exception e) {
            System.out.println("Error al analizar el TEXTO DB");
            e.printStackTrace();
            

        }

    }


    public ArrayList<Usuario> getListaUsuario() {
        return listaUsuario;
    }
    public String getMensaj_Prueba() {
        return mensaj_Prueba;
    }
    public ArrayList<Trivia> getListaTriva() {
        return listaTriva;
    }
    public ArrayList<Componente> getListaRegistro() {
        return listaRegistro;
    }

}
