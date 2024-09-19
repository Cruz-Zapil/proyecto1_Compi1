package com.app.recursos.util;

import com.app.recursosdb.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LecturaArchivo {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private ArrayList<Map<String, String>> listUsuarios = new ArrayList<Map<String, String>>();
    private ArrayList<Map<String, String>> registros = null;
    private ArrayList<Map<String, String>> trivias = null;


    public void enListarUsu(ArrayList<Usuario> usuarios) {
        
        for (Usuario usuario : usuarios) {
           
            Map<String, String> usuarioMap = new HashMap<>();
            usuarioMap.put("USUARIO",usuario.getUsuario()); 
            usuarioMap.put("PASSWORD",usuario.getPassword());
            usuarioMap.put("NOMBRE",usuario.getNombre());
            usuarioMap.put("INSTITUCION",usuario.getInstitucion());
            usuarioMap.put("FECHA_CREACION",usuario.getFechaCreacion());
            usuarioMap.put("FECHA_MODIFICACION",usuario.getFechaMod());
            listUsuarios.add(usuarioMap);
        }
    }

    public void agregarUsuario(Map<String, String> nuevoUsu) {

        if (nuevoUsu != null) {
            listUsuarios.add(nuevoUsu);
        }else{
            System.out.println("nada que agregar ");
        }
        
        // guardar los cambios en el archivo
        try (FileWriter writer = new FileWriter("/webApp/usuario.db")) {

            // Como el archivo tiene el campo `usuario1`, lo agregamos manualmente
            writer.write("usuario1 ");
            writer.write(gson.toJson(listUsuarios)); // Usa el JSON bonito
            writer.flush();
            /// datos guardados
            System.out.println("Datos guardados de usuario");

        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: usuario.db");
            e.printStackTrace();
        }
    }

    public void editarUsuario(){

    }

    public void leerRegistro() {

    }

    public void leerTrivia() {

    }

}
