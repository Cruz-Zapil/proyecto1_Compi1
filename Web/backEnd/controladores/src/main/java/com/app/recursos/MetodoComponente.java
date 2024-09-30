package com.app.recursos;

import com.app.gramaticas.Errorx;
import com.app.gramaticas.Reporte;
import com.app.recursos.util.GuardarInfo;
import com.app.recursosdb.trivia.Componente;
import com.app.recursosdb.trivia.Trivia;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class MetodoComponente {

    private Multimap<String, String> parametros = ArrayListMultimap.create();

    private boolean exiIdTriv;
    private boolean exiIdComp;
    private boolean exiTipoComp;
    private boolean exiIdiceComp;
    private boolean exiTextoVisible;
    private boolean exiRespuesta;
    private boolean exiOpciones;
    private boolean exiFila;
    private boolean exiColumna;

    private boolean sizeIdTriv;
    private boolean sizeIdComp;
    private boolean sizeTipoComp;
    private boolean sizeIndiceComp;
    private boolean sizeTextoVisible;
    private boolean sizeOpciones;
    private boolean sizeRespuesta;
    private boolean sizeFila;
    private boolean sizeColumna;

    /// instanciamos la Clase ConexionDB

    private ConexionDB db = new ConexionDB();

    public void parametros(Multimap<String, String> parametros) {

        this.parametros = parametros;
        db.recopilarArchivos();
        db.analizarTrivia();

    }

    public void analizarParametros() {

        exiIdTriv = parametros.containsKey("TRIVIA");
        exiIdComp = parametros.containsKey("ID");
        exiTipoComp = parametros.containsKey("CLASE");
        exiIdiceComp = parametros.containsKey("INDICE");
        exiTextoVisible = parametros.containsKey("TEXTO_VISIBLE");
        exiRespuesta = parametros.containsKey("RESPUESTA");
        exiOpciones = parametros.containsKey("OPCIONES");
        exiFila = parametros.containsKey("FILAS");
        exiColumna = parametros.containsKey("COLUMNAS");

        sizeIdTriv = !exiIdTriv || parametros.get("TRIVIA").size() < 2;
        sizeIdComp = !exiIdComp || parametros.get("ID").size() < 2;
        sizeTipoComp = !exiTipoComp || parametros.get("CLASE").size() < 2;
        sizeIndiceComp = !exiIdiceComp || parametros.get("INDICE").size() < 2;
        sizeTextoVisible = !exiTextoVisible || parametros.get("TEXTO_VISIBLE").size() < 2;
        sizeRespuesta = !exiRespuesta || parametros.get("RESPUESTA").size() < 2;
        sizeOpciones = !exiOpciones || parametros.get("OPCIONES").size() < 2;
        sizeFila = !exiFila || parametros.get("FILAS").size() < 2;
        sizeColumna = !exiColumna || parametros.get("COLUMNAS").size() < 2;

    }

    /// para la creacion de un nuevo componente
    /// analizar los parametros para crear un componente
    /// ver si existe en la db el id del componente y ver si " existe la trivia en
    /// la db"
    /// ver que tipo de componente es
    /// indice, (se agrega automaticamente)
    /// tiene que tener un texto visible
    /// opciones solo, va en CheckBox, Radio y ComboBox
    /// fila y columna va en Campo texto y Area Texto

    @SuppressWarnings("null")
    public void crearComponente() {

        analizarParametros();

        if (!db.getListaTriva().isEmpty()) {

            boolean triviaExistente = false;
            boolean compExistente = false;

            if (exiIdTriv && exiIdComp && exiTipoComp && exiTextoVisible && exiRespuesta) {
                if (sizeIdTriv && sizeIdComp && sizeTipoComp && sizeTextoVisible && sizeRespuesta) {

                    // analizar si existe la trivia en la db

                    Trivia triviaSelect = null;
                    String idTrivia = parametros.get("TRIVIA").iterator().next();
                    String idComp = parametros.get("ID").iterator().next();

                    for (Trivia trivia : db.getListaTriva()) {

                        if (trivia.getId().equals(idTrivia)) {
                            triviaSelect = trivia;

                            triviaExistente = true;

                            if (triviaSelect.getComponentes() != null) {

                                for (Componente component : triviaSelect.getComponentes()) {

                                    if (component.getIdComp().equals(idComp)) {
                                        compExistente = true;
                                        System.out.println("Error, el componente ya existe");

                                        break;
                                    }
                                }
                            }

                        }
                    }

                    if (triviaExistente) {
                        if (!compExistente) {

                            String idCompo = parametros.get("ID").iterator().next();
                            String idTriviaC = parametros.get("TRIVIA").iterator().next();
                            String textoVisible = parametros.get("TEXTO_VISIBLE").iterator().next();
                            String clase = parametros.get("CLASE").iterator().next();
                            String opciones = "";
                            String columna = "";
                            String fila = "";
                            if (exiOpciones) {
                                opciones = parametros.get("OPCIONES").iterator().next();
                            }

                            String respuesta = parametros.get("RESPUESTA").iterator().next();

                            if (exiFila) {
                                fila = parametros.get("FILAS").iterator().next();
                            }
                            if (exiColumna) {
                                columna = parametros.get("COLUMNAS").iterator().next();
                            }

                            if (clase.equals("CHECKBOX") || clase.equals("RADIO") || clase.equals("COMBO")) {
                                if (!opciones.isEmpty()) {

                                    Componente componente = new Componente(idCompo, idTriviaC, clase, textoVisible,
                                            respuesta, opciones, 0, 0);
                                    triviaSelect.setComponente(componente);
                                    System.out.println("Componente creado");
                                    /// guradar la info

                                    GuardarInfo guardar = new GuardarInfo();
                                    guardar.guardarDatoTrivia(db.getListaTriva());
                                    System.out.println(" componente agregado");
                                    Reporte.agregarMensaje("AGREGAR_COMPONENTE", "Componente agregado exitosamente");

                                } else {
                                    System.out.println("Error Sintactico: El componente debe tener opciones");
                                    Errorx error = new Errorx("Sintáctico", "OPCIONES",
                                            "El componente debe tener opciones", 0, 0);
                                    Reporte.agregarError(error);
                                }

                            } else if (clase.equals("CAMPO_TEXTO") || clase.equals("AREA_TEXTO")) {

                                if (!fila.isEmpty() && !columna.isEmpty()) {
                                    Componente componente = new Componente(idCompo, idTrivia, clase, textoVisible,
                                            respuesta, null,
                                            Integer.parseInt(fila), Integer.parseInt(columna));
                                    triviaSelect.setComponente(componente);
                                    System.out.println("Componente creado");
                                    /// guradar la info
                                    GuardarInfo guardar = new GuardarInfo();
                                    guardar.guardarDatoTrivia(db.getListaTriva());
                                    System.out.println(" Componente Agreagado");
                                    Reporte.agregarMensaje("AGREGAR_COMPONENTE", "Componente agregado exitosamente");

                                } else {
                                    System.out.println("Error Sintactico: El componente debe tener fila y columna");
                                    Errorx error = new Errorx("Sintáctico", "FILA-COLUMNA",
                                            "El componente debe tener fila y columna", 0, 0);
                                    Reporte.agregarError(error);

                                }
                            }

                        } else {
                            System.out.println("El componente ya existe cambie de ID");
                            Reporte.agregarMensaje("CREAR_COMPONENTE", "El componente ya existe cambie de ID");
                        }
                    } else {
                        System.out.println("No existe la trivia en la DB");
                        Reporte.agregarMensaje("CREAR_COMPONENTE", "No existe la trivia en la DB");
                    }
                } else {
                    if (!sizeIdComp) {
                        System.out.println("Error Semantico: El ID del componente ya fue declarado");
                        Errorx error = new Errorx("Semántico", "ID_COMPONENTE", "ID_COMPONENTE ya fue declarado", 0, 0);
                        Reporte.agregarError(error);
                    }

                    if (!sizeIdTriv) {
                        System.out.println("Error Semantico: El ID de la trivia ya  fue declarado");
                        Errorx error = new Errorx("Semántico", "ID_TRIVIA", "ID_TRIVIA ya fue declarado", 0, 0);
                        Reporte.agregarError(error);
                    }

                    if (!sizeTipoComp) {
                        System.out.println("Error Semantico: El tipo de componente ya fue declarado");
                        Errorx error = new Errorx("Semántico", "TIPO_COMPONENTE", "TIPO_COMPONENTE ya fue declarado", 0,
                                0);
                        Reporte.agregarError(error);
                    }

                    if (!sizeTextoVisible) {
                        System.out.println("Error Semantico: El texto visible ya fue declarado");
                        Errorx error = new Errorx("Semántico", "TEXTO_VISIBLE", "TEXTO_VISIBLE ya fue declarado", 0, 0);
                        Reporte.agregarError(error);
                    }

                    if (!sizeRespuesta) {
                        System.out.println("Error Semantico: La respuesta ya fue declarado");
                        Errorx error = new Errorx("Semántico", "RESPUESTA", "RESPUESTA ya fue declarado", 0, 0);
                        Reporte.agregarError(error);
                    }

                }
            } else {

                if (!exiIdTriv) {
                    System.out.println("Error Sintactico: Se esperaba el ID de la trivia");
                    Errorx error = new Errorx("Sintáctico", "ID_TRIVIA", "ID_TRIVIA no fue declarado", 0, 0);
                    Reporte.agregarError(error);
                }
                if (!exiIdComp) {
                    System.out.println("Error sintaico: Se esperaba el ID del componente");
                    Errorx error = new Errorx("Sintáctico", "ID_COMPONENTE", "ID_COMPONENTE no fue declarado", 0, 0);
                    Reporte.agregarError(error);
                }
                if (!exiTipoComp) {
                    System.out.println("Error Sintactico: Se esperaba el tipo de componente");
                    Errorx error = new Errorx("Sintáctico", "TIPO_COMPONENTE", "TIPO_COMPONENTE no fue declarado", 0,
                            0);
                    Reporte.agregarError(error);
                }
                if (!exiTextoVisible) {
                    System.out.println("Error Sintactico: Se esperaba el texto visible");
                    Errorx error = new Errorx("Sintáctico", "TEXTO_VISIBLE", "TEXTO_VISIBLE no fue declarado", 0, 0);
                    Reporte.agregarError(error);
                }
                if (!exiRespuesta) {
                    System.out.println("Error Sintactico: Se esperaba la respuesta");
                    Errorx error = new Errorx("Sintáctico", "RESPUESTA", "RESPUESTA no fue declarado", 0, 0);
                    Reporte.agregarError(error);
                }

            }
        } else {
            System.out.println("No hay trivias en la DB");
            Reporte.agregarMensaje("CREAR_COMPONENTE", "No hay trivias en la DB");
        }
    }

    //// para la creacion de una modificacion de un compontente
    //// parametros obligatorios: id_trivia y id componente
    //// un parametro opcional: indice, nombre, clase, texto_visible, opciones, fila
    //// y columna
    //// errores sintaticos y semanticos

    @SuppressWarnings("null")
    public void modificarComponente() {

        analizarParametros();
        if (!db.getListaTriva().isEmpty()) {

            boolean triviaExistente = false;
            boolean compExistente = false;
            boolean parametrosMod = false;
            Componente componenteSelect = null;

            if (exiIdTriv && exiIdComp) {
                if (sizeIdComp && sizeIdTriv) {

                    // analizar si existe la trivia en la db

                    String idTrivia = parametros.get("TRIVIA").iterator().next();
                    String idComp = parametros.get("ID").iterator().next();

                    for (Trivia trivia : db.getListaTriva()) {

                        if (trivia.getId().equals(idTrivia)) {
                            Trivia triviaSelect = trivia;

                            triviaExistente = true;
                            for (Componente component : triviaSelect.getComponentes()) {

                                if (component.getIdComp().equals(idComp)) {
                                    compExistente = true;
                                    componenteSelect = component;
                                    System.out.println("Componente existe");
                                    break;
                                }
                            }
                        }
                    }

                    if (triviaExistente) {
                        if (compExistente) {

                            if (sizeTipoComp && sizeTextoVisible && sizeRespuesta && sizeOpciones && sizeFila
                                    && sizeColumna) {

                                if (exiTipoComp) {
                                    String clase = parametros.get("CLASE").iterator().next();
                                    componenteSelect.setClase(clase);
                                    System.out.println("Clase modificada");
                                    parametrosMod = true;
                                }

                                if (exiTextoVisible) {
                                    String textoVisible = parametros.get("TEXTO_VISIBLE").iterator().next();
                                    componenteSelect.setTextoVisible(textoVisible);
                                    System.out.println("Texto visible modificado");
                                    parametrosMod = true;
                                }

                                if (exiRespuesta) {
                                    String respuesta = parametros.get("RESPUESTA").iterator().next();
                                    componenteSelect.setRespuesta(respuesta);
                                    System.out.println("Respuesta modificada");
                                    parametrosMod = true;
                                }

                                if (exiOpciones) {
                                    String opciones = parametros.get("OPCIONES").iterator().next();
                                    componenteSelect.setOpciones(opciones);
                                    System.out.println("Opciones modificadas");
                                    parametrosMod = true;
                                }

                                if (exiFila) {
                                    String fila = parametros.get("FILAS").iterator().next();
                                    componenteSelect.setFila(Integer.parseInt(fila));
                                    System.out.println("Fila modificada");
                                    parametrosMod = true;
                                }

                                if (exiColumna) {
                                    String columna = parametros.get("COLUMNAS").iterator().next();
                                    componenteSelect.setColumna(Integer.parseInt(columna));
                                    System.out.println("Columna modificada");
                                    parametrosMod = true;
                                }

                                if (parametrosMod) {
                                    System.out.println("Componente modificado");
                                    /// guradar la info

                                    GuardarInfo guardar = new GuardarInfo();
                                    guardar.guardarDatoTrivia(db.getListaTriva());
                                    System.out.println(" datos ya actualizados");

                                    Reporte.agregarMensaje("MODIFICAR_COMPONENTE",
                                            "Componente modificado exitosamente");

                                } else {
                                    System.out.println("Error Sintactico: No hay parametros para modificar");
                                    Errorx error = new Errorx("Sintáctico", "PARAMETROS",
                                            "No hay parametros para modificar", 0, 0);
                                    Reporte.agregarError(error);
                                }

                            } else {

                                if (!sizeTipoComp) {
                                    System.out.println("Error Semantico: El tipo de componente ya fue declarado");
                                    Errorx error = new Errorx("Semántico", "TIPO_COMPONENTE",
                                            "Parámetro ya fue declarado", 0, 0);
                                    Reporte.agregarError(error);

                                }

                                if (!sizeTextoVisible) {
                                    System.out.println("Error Semantico: El texto visible ya fue declarado");
                                    Errorx error = new Errorx("Semántico", "TEXTO_VISIBLE",
                                            "Parámetro ya fue declarado", 0, 0);
                                    Reporte.agregarError(error);

                                }
                                if (!sizeRespuesta) {
                                    System.out.println("Error Semantico: La respuesta ya fue declarado");
                                    Errorx error = new Errorx("Semántico", "RESPUESTA", "Parámetro ya fue declarado", 0,
                                            0);
                                    Reporte.agregarError(error);

                                }

                                if (!sizeOpciones) {
                                    System.out.println("Error Semantico: Las opciones ya fue declarado");
                                    Errorx error = new Errorx("Semántico", "OPCIONES", "Parámetro ya fue declarado", 0,
                                            0);
                                    Reporte.agregarError(error);

                                }

                                if (!sizeFila) {
                                    System.out.println("Error Semantico: La fila ya fue declarado");
                                    Errorx error = new Errorx("Semántico", "FILA", "Parámetro ya fue declarado", 0, 0);
                                    Reporte.agregarError(error);

                                }

                                if (!sizeColumna) {
                                    System.out.println("Error Semantico: La columna ya fue declarado");
                                    Errorx error = new Errorx("Semántico", "COLUMNA", "Parámetro ya fue declarado", 0,
                                            0);
                                    Reporte.agregarError(error);

                                }

                            }

                        } else {

                            System.out.println("No existe el componente en la DB");
                            Reporte.agregarMensaje("MODIFICAR_COMPONENTE", "No existe el componente en la TRIVIA");
                        }

                    } else {
                        System.out.println("No existe la trivia en la DB");
                        Reporte.agregarMensaje("MODIFICAR_COMPONENTE", "No existe la trivia en la DB");
                    }

                } else {

                    if (!sizeIdComp) {
                        System.out.println("Error Semantico: El ID del componente ya fue declarado");
                        Errorx error = new Errorx("Semántico", "ID_COMPONENTE", "ID_COMPONENTE ya fue declarado", 0, 0);
                        Reporte.agregarError(error);
                    }
                    if (!sizeIdTriv) {
                        System.out.println("Error Semantico: El ID de la trivia ya  fue declarado");
                        Errorx error = new Errorx("Semántico", "ID_TRIVIA", "ID_TRIVIA ya fue declarado", 0, 0);
                        Reporte.agregarError(error);
                    }
                }
            } else {

                if (!exiIdTriv) {
                    System.out.println("Error Sintacico: Se esperaba el ID de la trivia");
                    Errorx error = new Errorx("Sintáctico", "ID_TRIVIA", "ID_TRIVIA no fue declarado", 0, 0);
                    Reporte.agregarError(error);
                }
                if (!exiIdComp) {
                    System.out.println("Error sintaico: Se esperaba el ID del componente");
                    Errorx error = new Errorx("Sintáctico", "ID_COMPONENTE", "ID_COMPONENTE no fue declarado", 0, 0);
                    Reporte.agregarError(error);
                }

            }
        } else {
            System.out.println("No hay trivias en la DB");
            Reporte.agregarMensaje("MODIFICAR_COMPONENTE", "No hay trivias en la DB");
        }
    }

    /// para la eliminacion de un componente
    /// parametros obligatorios: id del componente y id de la trivia:
    ///
    public void eliminarComponente() {

        analizarParametros();
        if (!db.getListaTriva().isEmpty()) {

            boolean triviaExistente = false;
            boolean compExistente = false;

            if (exiIdTriv && exiIdComp) {
                if (sizeIdTriv && sizeIdComp) {

                    // analizar si existe la trivia en la db

                    String idTrivia = parametros.get("TRIVIA").iterator().next();
                    String idComp = parametros.get("ID").iterator().next();

                    for (Trivia trivia : db.getListaTriva()) {

                        if (trivia.getId().equals(idTrivia)) {
                            Trivia triviaSelect = trivia;

                            triviaExistente = true;
                            for (Componente component : triviaSelect.getComponentes()) {

                                if (component.getIdComp().equals(idComp)) {
                                    triviaSelect.getComponentes().remove(component);
                                    System.out.println("Componente eliminado");
                                    compExistente = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (triviaExistente) {
                        if (compExistente) {
                            System.out.println("Componente eliminado");
                            /// guradar la info

                            GuardarInfo guardar = new GuardarInfo();
                            guardar.guardarDatoTrivia(db.getListaTriva());
                            Reporte.agregarMensaje("ELIMINAR_COMPONENTE", "Componente eliminado exitosamente");
                        } else {
                            System.out.println("No existe el componente en la DB");
                            Reporte.agregarMensaje("ELIMINAR_COMPONENTE", "No existe el componente en la TRIVIA");
                        }
                    } else {
                        System.out.println("No existe la trivia en la DB");
                        Reporte.agregarMensaje("ELIMINAR_COMPONENTE", "No existe la trivia en la DB");
                    }
                } else {

                    if (!sizeIdComp) {
                        System.out.println("Error Semantico: El ID del componente ya fue declarado");
                        Errorx error = new Errorx("Semántico", "ID_COMPONENTE", "ID_COMPONENTE ya fue declarado", 0, 0);
                        Reporte.agregarError(error);
                    }
                    if (!sizeIdTriv) {
                        System.out.println("Error Semantico: El ID de la trivia ya  fue declarado");
                        Errorx error = new Errorx("Semántico", "ID_TRIVIA", "ID_TRIVIA ya fue declarado", 0, 0);
                        Reporte.agregarError(error);
                    }

                }
            } else {
                if (!exiIdTriv) {
                    System.out.println("Error Sintactico: El ID de la trivia ya fue declarado");
                    Errorx error = new Errorx("Sintáctico", "ID_TRIVIA", "ID_TRIVIA no fue declarado", 0, 0);
                    Reporte.agregarError(error);
                }
                if (!exiIdComp) {
                    System.out.println("Error Sintactico: El ID componente ya fue declarado");
                    Errorx error = new Errorx("Sintáctico", "ID_COMPONENTE", "ID no fue declarado", 0, 0);
                    Reporte.agregarError(error);
                }
            }
        } else {
            System.out.println("No hay trivias en la DB");
            Reporte.agregarMensaje("ELIMINAR_COMPONENTE", "No hay trivias en la DB");
        }
    }
}