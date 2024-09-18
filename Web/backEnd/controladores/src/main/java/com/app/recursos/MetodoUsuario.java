package com.app.recursos;

import java.util.Collection;

import org.checkerframework.checker.units.qual.mm;

import com.app.recursosdb.Usuario;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class MetodoUsuario {

    private Multimap<String, String> nuevoUsuario = ArrayListMultimap.create();
    private Multimap<String, String> modUsuario = ArrayListMultimap.create();
    private Multimap<String, String> loginUsuario = ArrayListMultimap.create();
    ConexionDB db = new ConexionDB();
    String mmm ="";
    public String a ="";
 


    public void crearUsuario(Multimap<String, String> nuevoUsuario) {
        this.nuevoUsuario = nuevoUsuario;

        analizarDatosNew();
    }

    public void modificarUsuario(Multimap<String, String> modUsuario) {
        this.modUsuario = modUsuario;

        analizarDatosMod();
    }

    public String loginUsuario(Multimap<String, String> loginUsuario) {

   
        db.recopilarArchivos();
        db.analizarUsuario();


        this.loginUsuario = loginUsuario;
        return analizarDatosLogin();
    }

    // analizar datos: ver errores sintacticos y semanticos
    // ver si existe en el DB
    // datos obligatoriso: usuario, password, nombre e institucion

    private void analizarDatosNew() {

    }

    // analizar datos: ver errores Sintacticos y Semanticos
    // ver si existe usuario en DB
    // datos obligatorios: el usuario antiguo y minimo un parametro

    private void analizarDatosMod() {

    }


    // analizar datos: ver errores Sintacticos y Semanticos
    // ver si existe en el DB
    // parametros obligatorios: usuario y password

    private String analizarDatosLogin() {
        // Verificar que ambos parámetros existan: "USUARIO" y "PASSWORD"
        boolean existeUsuario = loginUsuario.containsKey("USUARIO");
        boolean existePassword = loginUsuario.containsKey("PASSWORD");

        // Verificar si ambos parámetros tienen menos de 2 valores
        boolean sizeUsuario = loginUsuario.get("USUARIO").size() < 2;
        boolean sizePassword = loginUsuario.get("PASSWORD").size() < 2;
        boolean loggin = false;

        if (!db.getListaUsuario().isEmpty()) {

            if (existePassword && existeUsuario) {
                // Ambos parámetros existen
                if (sizePassword && sizeUsuario) {
                    // Analizar si existe en la DB
                    Collection<String> usu = loginUsuario.get("USUARIO");
                    Collection<String> pass = loginUsuario.get("PASSWORD");

                    for (Usuario usuario : db.getListaUsuario()) {
                        // Iterar sobre los usuarios y contraseñas del Multimap
                        for (String user : usu) {
                            for (String password : pass) {
                                if (usuario.getUsuario().equals(user) && usuario.getPassword().equals(password)) {
                                    System.out.println("Usuario logeado");
                                    loggin = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (!loggin) {
                        System.out.println("Usuario o contraseña incorrecta");
                    }
                } else {
                    if (!sizePassword) {
                        // Error semántico
                        return "Error Semántico: password ya fue declarado";
                    }

                    if (!sizeUsuario) {
                        // Error semántico
                        return "Error Semántico: usuario ya fue declarado";
                    }
                }
            } else {
                if (!existePassword) {
                    // Error sintáctico
                    return "Error Sintáctico: password no fue declarado";
                }

                if (!existeUsuario) {
                    // Error sintáctico
                    return "Error Sintáctico: usuario no fue declarado";
                }
            }

        } else {
            // No hay usuarios en la DB
            return "No hay usuarios en la base de datos";

        }

        if (loggin) {
            return "Usuario logeado";
        } else {
            return "Usuario o contraseña incorrecta";
        }
    }

}
