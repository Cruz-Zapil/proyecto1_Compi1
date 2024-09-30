package com.app.gramaticas;

import java.util.ArrayList;
import java.util.List;

public class Reporte {

    private static List<Errorx> listaErrores = new ArrayList<>();
    private static List<String> listaMensajes = new ArrayList<>();

    // Método estático para agregar errores
    public static void agregarError(Errorx error) {
        listaErrores.add(error);
    }

    // Método estático para obtener la lista de errores
    public static List<Errorx> getListaErrores() {
        return listaErrores;
    }

    // Método estático para limpiar la lista de errores
    public static void limpiarErrores() {
        listaErrores.clear();
    }

    /// METODO PARA AGREGAR MENSAJE
    public static void agregarMensaje(String nombre, String mensaje) {
        String mensajeNuevo = "<?xson version=\"1.0\" ?> \n" +
                "<!envio_respuestas:" + nombre + "> " + mensaje + " <!fin_envio_respuesta>";
        listaMensajes.add(mensajeNuevo);
    }

    // Método estático para obtener la lista de mensajes
    public static List<String> getListaMensajes() {
        return listaMensajes;
    }

    // Método estático para limpiar la lista de mensajes
    public static void limpiarMensajes() {
        listaMensajes.clear();

    }
}
