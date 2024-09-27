package com.app.recursos;

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
        exiFila = parametros.containsKey("FILA");
        exiColumna = parametros.containsKey("COLUMNA");

        sizeIdTriv = !exiIdTriv || parametros.get("TRIVIA").size() < 2;
        sizeIdComp = !exiIdComp || parametros.get("ID").size() < 2;
        sizeTipoComp = !exiTipoComp || parametros.get("CLASE").size() < 2;
        sizeIndiceComp = !exiIdiceComp || parametros.get("INDICE").size() < 2;
        sizeTextoVisible = !exiTextoVisible || parametros.get("TEXTO_VISIBLE").size() < 2;
        sizeRespuesta = !exiRespuesta || parametros.get("RESPUESTA").size() < 2;
        sizeOpciones = !exiOpciones || parametros.get("OPCIONES").size() < 2;
        sizeFila = !exiFila || parametros.get("FILA").size() < 2;
        sizeColumna = !exiColumna || parametros.get("COLUMNA").size() < 2;

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
                            for (Componente component : triviaSelect.getComponentes()) {

                                if (component.getIdComp().equals(idComp)) {
                                    compExistente = true;
                                    System.out.println("Error, el componente ya existe");
                                    break;
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
                            String opciones = parametros.get("OPCIONES").iterator().next();
                            String respuesta = parametros.get("RESPUESTA").iterator().next();
                            String fila = parametros.get("FILA").iterator().next();
                            String columna = parametros.get("COLUMNA").iterator().next();

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

                                } else {
                                    System.out.println("Error Sintactico: El componente debe tener opciones");
                                }

                            } else if (clase.equals("CAMPO_TEXTO") || clase.equals("AREA_TEXTO")) {

                                if (!fila.isEmpty() || !columna.isEmpty()) {
                                    Componente componente = new Componente(idCompo, idTrivia, clase, textoVisible,
                                            respuesta, null,
                                            Integer.parseInt(fila), Integer.parseInt(columna));
                                    triviaSelect.setComponente(componente);
                                    System.out.println("Componente creado");
                                    /// guradar la info
                                    GuardarInfo guardar = new GuardarInfo();
                                    guardar.guardarDatoTrivia(db.getListaTriva());
                                    System.out.println(" Componente Agreagado");

                                } else {
                                    System.out.println("Error Sintactico: El componente debe tener fila y columna");
                                }
                            }

                        } else {
                            System.out.println("El componente ya existe cambie de ID");
                        }
                    } else {
                        System.out.println("No existe la trivia en la DB");
                    }
                } else {
                    if (!sizeIdComp) {
                        System.out.println("Error Semantico: El ID del componente ya fue declarado");
                    }

                    /// agregar mas errores

                }
            } else {

                /// agregar mas errores

            }
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

                            if (sizeTipoComp || sizeTextoVisible || sizeRespuesta || sizeOpciones || sizeFila
                                    || sizeColumna) {

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
                                    String fila = parametros.get("FILA").iterator().next();
                                    componenteSelect.setFila(Integer.parseInt(fila));
                                    System.out.println("Fila modificada");
                                    parametrosMod = true;
                                }

                                if (exiColumna) {
                                    String columna = parametros.get("COLUMNA").iterator().next();
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

                                } else {
                                    System.out.println("Error Sintactico: No hay parametros para modificar");
                                }

                            } else {



                            }

                        } else {

                            System.out.println("No existe el componente en la DB");
                        }

                    } else {
                        System.out.println("No existe la trivia en la DB");
                    }

                } else {

                    if (!sizeIdComp) {
                        System.out.println("Error Semantico: El ID del componente ya fue declarado");
                    }
                    if (!sizeIdTriv) {
                        System.out.println("Error Semantico: El ID de la trivia ya  fue declarado");
                    }
                }
            } else {
               

                if (!exiIdTriv) {
                    System.out.println("Error Sintacico: Se esperaba el ID de la trivia");
                }
                if (!exiIdComp) {
                    System.out.println("Error sintaico: Se esperaba el ID del componente");
                }

            }
        }else {
            System.out.println("No hay trivias en la DB");
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
                        } else {
                            System.out.println("No existe el componente en la DB");
                        }
                    } else {
                        System.out.println("No existe la trivia en la DB");
                    }
                } else {

                    if (!sizeIdComp) {
                        System.out.println("Error Semantico: El ID del componente ya fue declarado");
                    }
                    if (!sizeIdTriv) {
                        System.out.println("Error Semantico: El ID de la trivia ya  fue declarado");
                    }

                }
            } else {
                if (!exiIdTriv) {
                    System.out.println("Error Semantico: El ID de la trivia ya fue declarado");
                }
                if (!exiIdComp) {
                    System.out.println("Error Semantico: El ID componente ya fue declarado");
                }
            }
        } else {
            System.out.println("No hay trivias en la DB");
        }
    }
}