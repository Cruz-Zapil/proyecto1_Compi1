package com.app.zzz;

import java.util.ArrayList;
import java.util.List;

import com.app.recursos.ConexionDB;
import com.app.recursosdb.trivia.Trivia;
import com.google.gson.Gson;

public class ManiComp {


    
public static void main(String[] args) {
    
 
     // Lógica para obtener las trivias del usuario
        List<Trivia> trivias = new ArrayList<>();

        ConexionDB conexionDB = new ConexionDB();
        conexionDB.recopilarArchivos();
        conexionDB.analizarTrivia();
      
        for (Trivia trivia : conexionDB.getListaTriva()) {

            if (trivia.getUsuario().equals("MariaMMQ")) {
                trivias.add(trivia);
            }   
        }

        // Convertir la lista a JSON usando una librería como Gson
        Gson gson = new Gson();
        String json = gson.toJson(trivias);

        System.out.println(json);
}
}
