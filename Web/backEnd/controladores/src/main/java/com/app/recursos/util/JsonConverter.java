package com.app.recursos.util;


import com.app.recursos.ConexionDB;
import com.app.recursosdb.registro.RegComponent;
import com.app.recursosdb.registro.Registro;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

public class JsonConverter {

    private static final Gson gson = new Gson();

    public static Registro fromJson(String jsonData) {
        
      
        Registro registro = null;

        try {
            JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
            String idTrivia = jsonObject.get("ID_TRIVIA").getAsString();
            String usuario = jsonObject.get("USUARIO").getAsString();
            int tiempo = jsonObject.get("TIEMPO").getAsInt();
            int punteo = jsonObject.get("PUNTEO").getAsInt();

            // Convertir RESPUESTAS a una lista de RegComponent
            List<RegComponent> listRegistroC = new ArrayList<>();
            JsonArray respuestasArray = jsonObject.getAsJsonArray("RESPUESTAS");

            for (int i = 0; i < respuestasArray.size(); i++) {
                JsonObject respuestaObject = respuestasArray.get(i).getAsJsonObject();
                String idComponent = respuestaObject.get("ID").getAsString();
                String registroValue = respuestaObject.get("REGISTRO").getAsString();
                String respuesta = respuestaObject.get("RESPUESTA").getAsString();

                // Crear un objeto RegComponent y agregarlo a la lista
                RegComponent componente = new RegComponent(idComponent, registroValue, respuesta);
                listRegistroC.add(componente);
            }

            // Crear el objeto Registro con todos los datos
            registro = new Registro(idTrivia, usuario, tiempo, punteo, listRegistroC);

            ConexionDB conexionDB = new ConexionDB();
            conexionDB.recopilarArchivos();
            conexionDB.analizarRegistro();

            List <Registro> listRegistro = conexionDB.getListRegistro();
            listRegistro.add(registro);
         
            GuardarInfo guardarInfo = new GuardarInfo();
            guardarInfo.guardarDatoReg(listRegistro);



        } catch (JsonSyntaxException e) {
            e.printStackTrace(); 
        }

        return registro;
    }
}
