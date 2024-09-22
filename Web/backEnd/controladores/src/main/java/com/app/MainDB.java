package com.app;

import java.io.Reader;
import java.io.StringReader;

import org.checkerframework.checker.units.qual.s;

import com.app.gramaticas.db.LexerDB;
import com.app.gramaticas.db.parserDB;
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

        


        Reader  triviaReader = new StringReader(trivia1);
        LexerDB lexerDB = new LexerDB(triviaReader);
        parserDB parserDB = new parserDB(lexerDB);
        parserDB.parse();



        if (!parserDB.getTrivias().isEmpty()) {

        // Mostrar el contenido de cada trivia y sus componentes
        for (Trivia trivia : parserDB.getTrivias()) {
            System.out.println("Trivia:");
            System.out.println("ID: " + trivia.getId());
            System.out.println("Nombre: " + trivia.getNombre());
            System.out.println("Tema: " + trivia.getTema());
            System.out.println("Tiempo: " + trivia.getTiempo_pre() + " segundos");
            System.out.println("Usuario: " + trivia.getUsuario());

            // Mostrar los componentes de cada trivia
            System.out.println("Componentes:");
            for (Componente componente : trivia.getComponentes()) {
                System.out.println("  ID Componente: " + componente.getIdComp());
                System.out.println("  ID Trivia: " + componente.getTrivia());
                System.out.println("  Clase: " + componente.getClase());
                System.out.println("  Texto Visible: " + componente.getTextoVisible());
                System.out.println("  Opciones: " + componente.getOpciones());
                System.out.println("  Respuesta: " + componente.getRespuesta());
                System.out.println("  Fila: "+ componente.getFila());
                System.out.println("  Columna: "+ componente.getColumna());
            }
            System.out.println();
        }

        }else {
            System.out.println("No se encontró el archivo de trivia.");
        }


    }
    
}
