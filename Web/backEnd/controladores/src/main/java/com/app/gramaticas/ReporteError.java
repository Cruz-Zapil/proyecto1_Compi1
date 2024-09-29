package com.app.gramaticas;

import java.util.ArrayList;
import java.util.List;

public class ReporteError {

    private static List<Errorx> listaErrores = new ArrayList<>();

    // Método estático para agregar errores
    public static void agregarError(Errorx error) {
        listaErrores.add(error);
    }

    // Método estático para obtener la lista de errores
    public static List<Errorx> getListaErrores() {
        return listaErrores;
    }

}
