package com.app;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import org.checkerframework.checker.units.qual.s;

import com.app.gramaticas.db.LexerDB;
import com.app.gramaticas.db.parserDB;
import com.app.recursos.ConexionDB;
import com.app.recursos.ConexionXson;
import com.app.recursosdb.trivia.Componente;
import com.app.recursosdb.trivia.Trivia;

public class MainDB {

    public static void main(String[] args) throws Exception {
        String trivia1 = "trivia1[\n" +
        "    {\n" +
        "    \"ID_TRIVIA\": \"$id_trivia1\",  \n" +
        "    \"NOMBRE\": \"cultura general\", \n" +
        "    \"TEMA\": \"cultura\",\n" +
        "    \"TIEMPO\": 10,\n" +
        "    \"USUARIO_CREACION\": \"hola mundo 1\", \n" +
        "    \"FECHA_CREACION\": \"2021-07-01\", \n" +
        "    \"ESTRUCTURA\": [\n" +
        "        {\n" +
        "            \"ID\": \"$_soldado\",  \n" +
        "            \"TRIVIA\": \"$id_trivia1\",  \n" +
        "            \"CLASE\": \"CHECKBOX\",  \n" +
        "            \"INDICE\": 1, \n" +
        "            \"TEXTO_VISIBLE\": \"Quienes fueron parte de la revolucion del 20 de octubre \",  \n" +
        "            \"OPCIONES\": \"Jacobo Arbenz|Jose Arebalo|Jorge Ubico|Maria Chichilla\", \n" +
        "            \"RESPUESTA\": \"Jacobo Arbenz|Jose Arebalo\" \n" +
        "        },\n" +
        "        {\n" +
        "            \"ID\": \"$_soldado_presidente\",  \n" +
        "            \"TRIVIA\": \"$trivia1\",  \n" +
        "            \"RESPUESTA\": \"Jacobo Arbenz\", \n" +
        "            \"INDICE\": 2,  \n" +
        "            \"CLASE\": \"RADIO\",  \n" +
        "            \"TEXTO_VISIBLE\": \"A quie se le conoce como el soldado del pueblo? \",\n" +
        "            \"OPCIONES\": \"Jacobo Arbenz|Jose Arebalo|Jorge Ubico|Otro\"\n" +
        "        }\n" +
        "    ]\n" +
        "    }\n" +
        "]";

        String nuevaTrivia = "<?xson version=\"1.0\" ?>\n" +
                "<!realizar_solicitud: \"NUEVA_TRIVIA\" >" +
                "{ \"PARAMETROS_TRIVIA\":[" +
                "{ \"ID_TRIVIA\": \"$trivia1\", " +
                "\"TIEMPO_PREGUNTA\": 45, " +
                "\"NOMBRE\": \"Cultura de Guatemala\", " +
                "\"TEMA\": \"cultura\" " +
                "}" +
                "]}" +
                "<fin_solicitud_realizada!>";

                String eliminarTrivia = "<?xson version=\"1.0\" ?>\n" +

                "<!realizar_solicitud: \"ELIMINAR_TRIVIA\" >\n" +
                   "{\n" +
                   "    \"PARAMETROS_TRIVIA\":[{\n" +
                   "        \"ID_TRIVIA\": \"$idtrivia1\"\n" +
                   "    }]\n" +
                   "}\n" +
                   "<fin_solicitud_realizada!>";





        ConexionXson conexionXson = new ConexionXson();
        conexionXson.analizadorGeneral(eliminarTrivia);


        







    }
    
}
