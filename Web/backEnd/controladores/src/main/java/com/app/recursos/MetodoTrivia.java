package com.app.recursos;

import java.security.Guard;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.app.recursos.util.GuardarInfo;
import com.app.recursosdb.trivia.Componente;
import com.app.recursosdb.trivia.Trivia;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class MetodoTrivia {

    private Multimap<String, String> parametros = ArrayListMultimap.create();

    private boolean exiIdTrivia;
    private boolean exiTmpPregunta;
    private boolean exiNomTrivia;
    private boolean exiTemaTrivia;
    private boolean exiUsuCreacion;
    private boolean exiFecCreacion;

    private boolean sizeIdTrivia;
    private boolean sizeTmpPregunta;
    private boolean sizeNomTrivia;
    private boolean sizeTemaTrivia;
    private boolean sizeUsuCreacion;
    private boolean sizeFecCreacion;

    private ConexionDB db = new ConexionDB();

    public void parametros(Multimap<String, String> parametros) {
        this.parametros = parametros;
        db.recopilarArchivos();
        db.analizarTrivia();
    }

    public void analizarParametros() {
        exiIdTrivia = parametros.containsKey("ID_TRIVIA");
        exiTmpPregunta = parametros.containsKey("TIEMPO_PREGUNTA");
        exiNomTrivia = parametros.containsKey("NOMBRE");
        exiTemaTrivia = parametros.containsKey("TEMA");
        exiUsuCreacion = parametros.containsKey("USUARIO_CREACION");
        exiFecCreacion = parametros.containsKey("FECHA_CREACION");

        sizeIdTrivia = !exiIdTrivia || (parametros.get("ID_TRIVA").size() < 2);
        sizeTmpPregunta = !exiTmpPregunta || (parametros.get("TIEMPO_PREGUNTA").size() < 2);
        sizeNomTrivia = !exiNomTrivia || (parametros.get("NOMBRE").size() < 2);
        sizeTemaTrivia = !exiTemaTrivia || (parametros.get("TEMA").size() < 2);
        sizeUsuCreacion = !exiUsuCreacion || (parametros.get("USUARIO_CREACION").size() < 2);
        sizeFecCreacion = !exiFecCreacion || (parametros.get("FECHA_CREACION").size() < 2);

    }

    /// analizar los parametros para crear una trivia
    // ver si id existe en la db
    // datos obligatorios: id, nombre, tiempoPregunta, tema
    // datos opcionales: fecha_creacion y usuario_creacion

    public void crearTrivia() {
        analizarParametros();
        if (!db.getListaTriva().isEmpty()) {


            boolean triviaExistente = false;

            if (exiIdTrivia && exiTmpPregunta && exiNomTrivia && exiTemaTrivia) {

                if (sizeIdTrivia && sizeTmpPregunta && sizeNomTrivia && sizeTemaTrivia && sizeUsuCreacion
                        && sizeFecCreacion) {

                    String id = parametros.get("ID_TRIVIA").iterator().next();
                    String nombre = parametros.get("NOMBRE").iterator().next();
                    int tmpPregunta = Integer.parseInt(parametros.get("TIEMPO_PREGUNTA").iterator().next());
                    String tema = parametros.get("TEMA").iterator().next();
                    String usuCreacion ="";
                    String fecCreacion = "";

                    if (exiFecCreacion) {
                        /// crear fecha de creacion
                        fecCreacion = parametros.get("FECHA_CREACION").iterator().next();
                    } else {
                        // Obtener la fecha actual
                        LocalDate fechaActual = LocalDate.now();
                        // Formatear la fecha en el formato deseado
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        fecCreacion = fechaActual.format(formatter);
                    }

                    if (exiUsuCreacion) {
                        usuCreacion = parametros.get("USUARIO_CREACION").iterator().next();
                    }else {
                        usuCreacion = "Invitado";

                    }

                    for (Trivia trivia : db.getListaTriva()) {
                        if (trivia.getId().equals(id)) {
                            triviaExistente = true;
                            System.out.println("Trivia ya existe");
                            System.out.println("Cabia de ID: " + trivia.getId());
                            break;
                        }
                    }

                    if (!triviaExistente) {
                    
                        // crear la trivia :)
                        Trivia nueva =  new Trivia(id, nombre, tmpPregunta, usuCreacion, tema, fecCreacion, null);
                        db.getListaTriva().add(nueva);
                         // enviar la lista a Guardar info

                        GuardarInfo guardar = new GuardarInfo();
                        guardar.guardarDatoTrivia(db.getListaTriva());

                    }else {
                        System.out.println("Trivia ya existe");
                        System.out.println("Cabia de ID: ");
                    }

                } else {
                    /// ver errores semanticos

                }

            } else {
                /// ver errores sintacticos

            }

        } else {

            System.out.println(" No hay datos en la DB");
        }

    }

    /// analizar los parametros para modificar una trivia
    // ver si id existe en la db
    // datos obligatorios: id
    // datos opcionales: nombre, tiempoPregunta, tema

    @SuppressWarnings("null")
    public void modificarTrivia() {
        analizarParametros();

        if (!db.getListaTriva().isEmpty()) {

            boolean triviaExistente = false;
            boolean parametrosModificados = false;
            Trivia triviaModificada = null;

            if (exiIdTrivia) {

                if (sizeIdTrivia) {

                    String idTrivia = parametros.get("ID_TRIVA").iterator().next();

                    for (Trivia trivia : db.getListaTriva()) {

                        if (trivia.getId().equals(idTrivia)) {
                            triviaExistente = true;
                            triviaModificada = trivia;
                            break;
                        }
                    }

                    if (triviaExistente) {

                        if (sizeNomTrivia || sizeTmpPregunta || sizeTemaTrivia) {

                            if (exiNomTrivia) {
                                String nombre = parametros.get("NOMBRE").iterator().next();
                                triviaModificada.setNombre(nombre);
                                parametrosModificados = true;
                            }

                            if (exiTmpPregunta) {

                                int tmpPregunta = Integer.parseInt(parametros.get("TIEMPO_PREGUNTA").iterator().next());
                                triviaModificada.setTiempo_pre(tmpPregunta);
                                parametrosModificados = true;

                            }

                            if (exiTemaTrivia) {

                                String tema = parametros.get("TEMA").iterator().next();
                                triviaModificada.setTema(tema);
                                parametrosModificados = true;

                            }

                            if (parametrosModificados) {

                                //// lectura del archivo y escritura
                                GuardarInfo guardar = new GuardarInfo();
                                guardar.guardarDatoTrivia(db.getListaTriva());

                            }else {
                                System.out.println("Error Sintáctico: Se esperaba un parametro para modificar");
                            }

                        } else {

                            /// errores semanticos 
                            if(!sizeNomTrivia){
                                System.out.println("Error Semántico: Nombre de la trivia ya fue declarado");
                            }
                            if(!sizeTmpPregunta){
                                System.out.println("Error Semántico: Tiempo de la pregunta ya fue declarado");
                            }
                            if(!sizeTemaTrivia){
                                System.out.println("Error Semántico: Tema de la trivia ya fue declarado");
                            }
                        }
                    } else {
                        System.out.println("No se encontro la trivia en la DB");

                    }
                }else {
                    System.out.println("Error Semántico: ID trivia ya fue declarado");
                }
            }else {
                
                System.out.println("Error Sintáctico: falta el ID de la trivia");
            }
        }else {
            System.out.println(" No hay datos de trivia en la DB");
        }

    }

    public void eliminarTrivia() {
        analizarParametros();

        if (!db.getListaTriva().isEmpty()) {

            boolean triviaExistente = false;

            if (exiIdTrivia) {
                if (sizeIdTrivia) {

                    // analizar si existe la trivia en la DB

                    String id = parametros.get("ID_TRIVIA").iterator().next();

                    for (Trivia trivia : db.getListaTriva()) {

                        if (trivia.getId().equals(id)) {
                            db.getListaTriva().remove(trivia);
                            triviaExistente = true;
                            System.out.println("Trivia eliminada");
                            break;
                        }
                    }

                    if (triviaExistente) {
                        /// la lectura del archivo y la nueva escritura
                        GuardarInfo guardar = new GuardarInfo();
                        guardar.guardarDatoTrivia(db.getListaTriva());

                    } else {
                        System.out.println("Trivia no existe en la DB");
                    }

                } else {
                    /// ver errores semanticos
                    System.out.println("Error Semántico: ID trivia ya fue declarado");
                }

            } else {
                /// ver errores sintacticos
                System.out.println("Error Sintáctico: falta el ID de la trivia");
            }

        } else {
            System.out.println(" No hy datos en la DB");
        }
    }

}
