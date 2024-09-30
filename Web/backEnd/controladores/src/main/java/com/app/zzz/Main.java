package com.app.zzz;

import com.app.gramaticas.Errorx;
import com.app.gramaticas.Reporte;
import com.app.recursos.ConexionXson;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello, World!");

        String nuevoUsu= "<?xson version=\"1.0\" ?> <!realizar_solicitud: \"USUARIO_NUEVO\" >\n"
                + "{ \"DATOS_USUARIO\":[{ \"USUARIO\": \"Crucito\", \"PASSWORD\": \"1*5678\", \"NOMBRE\": \"Cruz Quij\", \"INSTITUCION\": \"USAC\"\n }\n"
             
                + "}\n"
                + "<fin_solicitud_realizada!>";

        String solicitud1 = "<?xson version=\"1.0\" ?>\n" +
                "<!realizar_solicitud: \"ELIMINAR_USUARIO\" >\n" +
                "{ \"DATOS_USUARIO\":[{ \"USUARIO\": \"juanitos\"\n" +
                " \"NOMBRE\": \"Ruiz\" " +
                "}\n" +
                "]}\n" +
                "<fin_solicitud_realizada!>";

        String solicitud2 = "<?xson version=\"1.0\" ?>\n" +
                "<!realizar_solicitud: \"MODIFICAR_USUARIO\" >\n" +
                "{ \"DATOS_USUARIO\":[{ \"USUARIO_ANTIGUO\": \"SexZapil\", \"INSTITUCION\": \"MONARCA cRUZ\"\n" +
                "}\n" +
                "]}\n" +
                "<fin_solicitud_realizada!>";

        String solicitud3 = "<?xson version=\"1.0\" ?>\n" +
                "<!realizar_solicitud: \"ELMINAR_USUARIO\" >\n" +
                "{ \"DATOS_USUARIO\":[{ \"USUARIO\": \"SexZapil\"\n" +
                "}\n" +
                "]}\n" +
                "<fin_solicitud_realizada!>";

        ConexionXson conexionXson = new ConexionXson();
        // conexionXson.analizadorGeneral(solicitud1);

        String login = " <?xson version=\"1.0\"?>" +
                "<!realizar_solicitud: \"LOGIN_USUARIO\">" +
                "{\"DATOS_USUARIO\":[{" +
                "\"USUARIO\":\"MariaMMQ\"," +
                "\"PASSWORD\":\"1asdf23456\"" +
                "}]" +
                "}" +
                "<fin_solicitud_realizada!>";

                conexionXson.analizarLogin(login);

   

        System.out.println("Errores: _____________________------ \n\n\n");

        
        for (Errorx error : Reporte.getListaErrores()) {
                
                System.out.println(error.toString());

        }
 
        String solicitudasdf= "<?xson version=\"1.0\" ?>\n" +
        "<!realizar_solicitudes>\n" +
        "<!realizar_solicitud: \"USUARIO_NUEVO\">\n" +
        "{ \"DATOS_USUARIO\":[{ \n" +
        "\"USUARIO\": \"Crucito\",\n" +
        "\"PASSWORD\": \"SD15678\",\n" +
        "\"NOMBRE\": \"Cruz Quij\",\n" +
        "\"INSTITUCION\": \"USAC\"\n" +
        "}]\n" +
        "}\n" +
        "<fin_solicitud_realizada!>\n" +
        "<!realizar_solicitud: \"USUARIO_NUEVO\" >\n" +
        "{\n" +
        "\"DATOS_USUARIO\":[{ \n" +
        "\"USUARIO\": \"HECTORH\",\n" +
        "\"PASSWORD\": \"SD156A\",\n" +
        "\"NOMBRE\": \"Herctor fort\",\n" +
        "\"INSTITUCION\": \"Barca\"\n" +
        "}]\n" +
        "}\n" +
        "<fin_solicitud_realizada!>\n" +
        "<!fin_solicitudes_realizada>";



        String triviaComponet= "<?xson version=\"1.0\" ?>\n" +
        "<!realizar_solicitudes>\n" +
        "<!realizar_solicitud: \"NUEVA_TRIVIA\">\n" +
        "{ \"PARAMETROS_TRIVIA\":[{ \n" +
        "\"ID_TRIVIA\": \"_trivUsac\",\n" +
        "\"TIEMPO_PREGUNTA\": 56,\n" +
        "\"NOMBRE\": \"Encueta usac\",\n" +
        "\"TEMA\": \"USAC\"\n" +
        "}]\n" +
        "}\n" +
        "<fin_solicitud_realizada!>\n" +
        "<!realizar_solicitud: \"USUARIO_NUEVO\" >\n" +
        "{\n" +
        "\"DATOS_USUARIO\":[{ \n" +
        "\"USUARIO\": \"auxiPreba\",\n" +
        "\"PASSWORD\": \"SD156A\",\n" +
        "\"NOMBRE\": \"Herctor fort\",\n" +
        "\"INSTITUCION\": \"Barca\"\n" +
        "}]\n" +
        "}\n" +
        "<fin_solicitud_realizada!>\n" +
        "<!realizar_solicitud: \"AGREGAR_COMPONENTE\" >\n" +
        "{\n" +
        "\"PARAMETROS_COMPONENTE\":[{ \n" +
        "\"ID\": \"_auxi_Preba\",\n" +
        "\"TRIVIA\": \"_trivUsac\",\n" +
        "\"CLASE\": \"CAMPO_TEXTO\",\n" +
        "\"TEXTO_VISIBLE\": \"año de de la usac\",\n"+
        "\"RESPUESTA\": \"346 años\",\n" +
        "\"FILAS\": 23,\n" +
        "\"COLUMNAS\": 10 \n" +
        "}]\n" +
        "}\n" +
        "<fin_solicitud_realizada!>\n" +

        "<!fin_solicitudes_realizada>";
        

        String componet= "<?xson version=\"1.0\" ?>\n" +
        "<!realizar_solicitudes>\n" +
        "<!realizar_solicitud: \"MODIFICAR_COMPONENTE\" >\n" +
        "{\n" +
        "\"PARAMETROS_COMPONENTE\":[{ \n" +
        "\"ID\": \"_auxi_Preba\",\n" +
        "\"TRIVIA\": \"_trivUsac\",\n" +
        "\"CLASE\": \"CAMPO_TEXTO\",\n" +
        "\"TEXTO_VISIBLE\": \"Funcacion de la Usac?\",\n"+
        "\"RESPUESTA\": \"1800\",\n" +
        "\"FILAS\": 23,\n" +
        "\"COLUMNAS\": 10 \n" +
        "}]\n" +
        "}\n" +
        "<fin_solicitud_realizada!>\n" +
        "<!fin_solicitudes_realizada>";
        
  //      conexionXson.analizadorGeneral(componet);



    }

}
