package com.app;

import com.app.recursos.ConexionXson;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello, World!");

        String xsonString = "<?xson version=\"1.0\" ?> <!realizar_solicitud: \"USUARIO_NUEVO\" >\n"
                + "{ \"DATOS_USUARIO\":[{ \"USUARIO\": \"Crucito\", \"PASSWORD\": \"1*5678\", \"NOMBRE\": \"Cruz Quij\", \"INSTITUCION\": \"USAC\"\n }\n"
                + "]\n"
                + "}\n"
                + "<fin_solicitud_realizada!>";

        String solicitud1 = "<?xson version=\"1.0\" ?>\n" +
                "<!realizar_solicitud: \"ELIMINAR_USUARIO\" >\n" +
                "{ \"DATOS_USUARIO\":[{ \"USUARIO\": \"SexZapil\"\n" +
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
        conexionXson.analizadorGeneral(solicitud1);

    }

}
