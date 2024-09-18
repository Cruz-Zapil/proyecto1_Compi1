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

public class ConexionDB {

    private ArrayList<Usuario> listaUsuario = new ArrayList<Usuario>();
    // public ArrayList<Usuario> listaTriva = new ArrayList<Usuario>();
    // public ArrayList<Usuario> listaRegistro = new ArrayList<Usuario>();

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
                    } else {
                        // Si hay otros archivos que no sean de tipo db, puedes manejarlos aquí
                        mensaj_Prueba += String.join("\n", lineas) + "\n";
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

            for (Usuario usu : listaUsuario) {
                mensaj_Prueba += "Usuario: " + usu.getUsuario() + "\n";
                mensaj_Prueba += "Password: " + usu.getPassword() + "\n";
                mensaj_Prueba += "Nombre: " + usu.getNombre() + "\n";

            }

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

            Reader reader = new StringReader(usuario);
            LexerDB lexer = new LexerDB(reader);
            parserDB parser = new parserDB(lexer);
            parser.parse();

            /// obtener todo los registros de la base de datos

            listaUsuario = parser.getUsuarios();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

  

    public ArrayList<Usuario> getListaUsuario() {

        return listaUsuario;
    }

}
