package com.app.recursos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.app.recursos.util.LecturaArchivo;
import com.app.recursosdb.Usuario;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class MetodoUsuario {

    private Multimap<String, String> parametros = ArrayListMultimap.create();
    private Multimap<String, String> loginUsuario = ArrayListMultimap.create();

    private boolean existeUsuarioNuevo;
    private boolean existeUsuarioAntiguo;
    private boolean existeUsuario;
    private boolean existePassword;
    private boolean existePasswordNuevo;
    private boolean existeNombre;
    private boolean existeInstitucion;
    private boolean existeFechaCreacion;
    private boolean existeFechaModificacion;

    private boolean sizeUsuarioNuevo;
    private boolean sizeUsuarioAntiguo;
    private boolean sizeUsuario;
    private boolean sizePassword;
    private boolean sizePasswordNuevo;
    private boolean sizeNombre;
    private boolean sizeInstitucion;
    private boolean sizeFechaCreacion;
    private boolean sizeFechaModificacion;

    private ConexionDB db = new ConexionDB();



    public void analizarParametros() {

        existeUsuarioNuevo = parametros.containsKey("USUARIO_NUEVO");
        existeUsuarioAntiguo = parametros.containsKey("USUARIO_ANTIGUO");
        existeUsuario = parametros.containsKey("USUARIO");
        existePassword = parametros.containsKey("PASSWORD");
        existePasswordNuevo = parametros.containsKey("NUEVO_PASSWORD");
        existeNombre = parametros.containsKey("NOMBRE");
        existeInstitucion = parametros.containsKey("INSTITUCION");
        existeFechaCreacion = parametros.containsKey("FECHA_CREACION");
        existeFechaModificacion = parametros.containsKey("FECHA_MODIFICACION");

        sizeUsuarioNuevo = !existeUsuarioNuevo || (parametros.get("USUARIO_NUEVO").size() < 2);
        sizeUsuarioAntiguo = !existeUsuarioAntiguo || (parametros.get("USUARIO_ANTIGUO").size() < 2);
        sizeUsuario = !existeUsuario || (parametros.get("USUARIO").size() < 2);
        sizePassword = !existePassword || (parametros.get("PASSWORD").size() < 2);
        sizePasswordNuevo = !existePasswordNuevo || (parametros.get("NUEVO_PASSWORD").size() < 2);
        sizeNombre = !existeNombre || (parametros.get("NOMBRE").size() < 2);
        sizeInstitucion = !existeInstitucion || (parametros.get("INSTITUCION").size() < 2);
        sizeFechaCreacion = !existeFechaCreacion
                || (existeFechaCreacion && parametros.get("FECHA_CREACION").size() < 2);
        sizeFechaModificacion = !existeFechaModificacion
                || (existeFechaModificacion && parametros.get("FECHA_MODIFICACION").size() < 2);

    }



    public void parametros(Multimap<String, String> parametros) {
        this.parametros = parametros;
        db.recopilarArchivos();
        db.analizarUsuario();
    }

    public String loginUsuario(Multimap<String, String> loginUsuario) {

        db.recopilarArchivos();
        db.analizarUsuario();

        this.loginUsuario = loginUsuario;
        return analizarDatosLogin();
    }

    // analizar datos: ver errores Sintacticos y Semanticos
    // ver si existe usuario en DB
    // datos obligatorios: el usuario antiguo y minimo un parametro

    @SuppressWarnings("null")
    public void analizarDatosMod() {

        if (!db.getListaUsuario().isEmpty()) {

            boolean usuarioExistente = false;
            boolean parametrosModificados = false;
            Usuario usuarioModificado = null;

            if (existeUsuarioAntiguo) {
                if (sizeUsuarioAntiguo) {

                    // Analizar si existe en la DB
                    String usuAntiguo = parametros.get("USUARIO_ANTIGUO").iterator().next();
                    System.out.println("Usuario antiguo: " + usuAntiguo);

                    for (Usuario usuario : db.getListaUsuario()) {
                        // Iterar sobre los usuarios y contraseñas del Multimap

                        if (usuario.getUsuario().equals(usuAntiguo)) {
                            usuarioModificado = usuario;
                            db.getListaUsuario().remove(usuario);
                            usuarioExistente = true;
                            break;
                        }
                    }

                    if (usuarioExistente) {


                        if (sizeUsuarioNuevo && sizePasswordNuevo && sizeInstitucion && sizeFechaModificacion) {

                            if (existeUsuarioNuevo) {
                                String usu = parametros.get("USUARIO_NUEVO").iterator().next();
                                usuarioModificado.setUsuario(usu);
                                parametrosModificados = true;
                            }

                            if (existePasswordNuevo) {
                                String pass = parametros.get("NUEVO_PASSWORD").iterator().next();
                                usuarioModificado.setPassword(pass);
                                parametrosModificados = true;
                            }

                            if (existeInstitucion) {
                                String inst = parametros.get("INSTITUCION").iterator().next();
                                usuarioModificado.setInstitucion(inst);
                                parametrosModificados = true;
                            }

                            if (existeFechaModificacion) {
                                // crear la fecha de modificacion
                                String fecha = parametros.get("FECHA_MODIFICACION").iterator().next();
                                usuarioModificado.setFechaMod(fecha);
    
                            } else {
                                // Obtener la fecha actual
                                LocalDate fechaActual = LocalDate.now();

                                // Formatear la fecha en el formato deseado
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                String fecha = fechaActual.format(formatter);
                                usuarioModificado.setFechaMod(fecha);
                            }
                            if (parametrosModificados) {

                                LecturaArchivo la = new LecturaArchivo();
                                db.getListaUsuario().add(usuarioModificado);
                                la.enListarUsu(db.getListaUsuario());
                                la.agregarUsuario(null);

                            } else {
                                System.out.println("Error sintactico: se esperaba al menos un parametro a modificar");
                            }

                        } else {

                            if (!sizeUsuarioNuevo) {
                                // Error semántico
                                System.out.println("Error Semántico: usuario nuevo ya fue declarado");
                            }

                            if (!sizePasswordNuevo) {
                                // Error semántico
                                System.out.println("Error Semántico: password nuevo ya fue declarado");
                            }

                            if (!sizeInstitucion) {
                                // Error semántico
                                System.out.println("Error Semántico: institucion ya fue declarado");
                            }

                            if (!sizeFechaModificacion) {
                                // Error semántico
                                System.out.println("Error Semántico: fecha de modificacion ya fue declarado");
                            }

                        }

                    } else {
                        System.out.println("Usuario no existe");
                    }

                } else {
                    System.out.println("Error Semántico: usuario antiguo ya fue declarado");
                }

            } else {
                System.out.println("No hay usuarios en la base de datos");
            }
        }
    }

    /// analizar datos: ver errores Sintacticos y Semanticos
    // ver si existe en el DB
    // datos obligatorios: el usuario

    public void analizarDatosDelete() {

        if (!db.getListaUsuario().isEmpty()) {
            boolean usuarioExistente = false;

            if (existeUsuario) {

                if (sizeUsuario) {

                    // Analizar si existe en la DB
                    String usu = parametros.get("USUARIO").iterator().next();

                    for (Usuario usuario : db.getListaUsuario()) {
                        // Iterar sobre los usuarios y contraseñas del Multimap

                        if (usuario.getUsuario().equals(usu)) {
                            db.getListaUsuario().remove(usuario);
                            usuarioExistente = true;
                            break;
                        }
                    }

                    if (usuarioExistente) {
                        LecturaArchivo la = new LecturaArchivo();
                        la.enListarUsu(db.getListaUsuario());
                        la.agregarUsuario(null);

                    } else {
                        System.out.println("Usuario no existe en la base de datos");
                    }

                } else {
                    System.out.println("Error Semántico: usuario ya fue declarado");
                }

            } else {
                // Error sintáctico
                System.out.println("Error Sintáctico: usuario no fue declarado");
            }

        } else {
            System.out.println("No hay usuarios en la base de datos");

        }

    }

    // analizar datos: ver errores sintacticos y semanticos
    // ver si existe en el DB
    // datos obligatoriso: usuario, password, nombre e institucion



    public void analizarDatosNew() {

        if (!db.getListaUsuario().isEmpty()) {

            boolean usuarioExistente = false;

            if (existeUsuario && existePassword && existeNombre && existeInstitucion) {
                if (sizeUsuario && sizePassword && sizeNombre && sizeInstitucion) {

                    // Analizar si existe en la DB
                    String usu = parametros.get("USUARIO").iterator().next();
                    String pass = parametros.get("PASSWORD").iterator().next();
                    String nom = parametros.get("NOMBRE").iterator().next();
                    String inst = parametros.get("INSTITUCION").iterator().next();
                    String fecha = "";

                    if (existeFechaCreacion) {
                        // crear la fecha de creacion
                        fecha = parametros.get("FECHA_CREACION").iterator().next();

                    } else {
                        // Obtener la fecha actual
                        LocalDate fechaActual = LocalDate.now();

                        // Formatear la fecha en el formato deseado
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        fecha = fechaActual.format(formatter);
                    }

                    for (Usuario usuario : db.getListaUsuario()) {
                        // Iterar sobre los usuarios y contraseñas del Multimap

                        if (usuario.getUsuario().equals(usu)) {
                            System.out.println("Usuario ya existe cambia de usuario");
                            usuarioExistente = true;
                            break;
                        }
                    }

                    if (!usuarioExistente) {

                        Map<String, String> listaUsuario = new HashMap<String, String>();
                        listaUsuario.put("USUARIO", usu);
                        listaUsuario.put("PASSWORD", pass);
                        listaUsuario.put("NOMBRE", nom);
                        listaUsuario.put("INSTITUCION", inst);
                        listaUsuario.put("FECHA_CREACION", fecha);

                        LecturaArchivo la = new LecturaArchivo();
                        la.enListarUsu(db.getListaUsuario());
                        la.agregarUsuario(listaUsuario);

                    }

                } else {
                    if (!sizeUsuario) {
                        // Error semántico
                        System.out.println("Error Semántico: usuario ya fue declarado");
                    }

                    if (!sizePassword) {
                        // Error semántico
                        System.out.println("Error Semántico: password ya fue declarado");
                    }

                    if (!sizeNombre) {
                        // Error semántico
                        System.out.println("Error Semántico: nombre ya fue declarado");
                    }

                    if (!sizeInstitucion) {
                        // Error semántico
                        System.out.println("Error Semántico: institucion ya fue declarado");
                    }

                    if (!sizeFechaCreacion) {
                        // Error semántico
                        System.out.println("Error Semántico: fecha de creacion ya fue declarado");
                    }
                }
            } else {
                if (!existeUsuario) {
                    // Error sintáctico
                    System.out.println("Error Sintáctico: usuario no fue declarado");
                }

                if (!existePassword) {
                    // Error sintáctico
                    System.out.println("Error Sintáctico: password no fue declarado");
                }

                if (!existeNombre) {
                    // Error sintáctico
                    System.out.println("Error Sintáctico: nombre no fue declarado");
                }

                if (!existeInstitucion) {
                    // Error sintáctico
                    System.out.println("Error Sintáctico: institucion no fue declarado");
                }

            }

        } else {
            System.out.println("No hay usuarios en la base de datos");
        }

    }

    // analizar datos: ver errores Sintacticos y Semanticos
    // ver si existe en el DB
    // parametros obligatorios: usuario y password

    private String analizarDatosLogin() {
        // Verificar que ambos parámetros existan: "USUARIO" y "PASSWORD"
        boolean existeUsuario = loginUsuario.containsKey("USUARIO");
        boolean existePassword = loginUsuario.containsKey("PASSWORD");

        // Verificar si ambos parámetros tienen menos de 2 valores
        boolean sizeUsuario = loginUsuario.get("USUARIO").size() < 2;
        boolean sizePassword = loginUsuario.get("PASSWORD").size() < 2;
        boolean loggin = false;

        if (!db.getListaUsuario().isEmpty()) {

            if (existePassword && existeUsuario) {
                // Ambos parámetros existen
                if (sizePassword && sizeUsuario) {
                    // Analizar si existe en la DB
                    Collection<String> usu = loginUsuario.get("USUARIO");
                    Collection<String> pass = loginUsuario.get("PASSWORD");

                    for (Usuario usuario : db.getListaUsuario()) {
                        // Iterar sobre los usuarios y contraseñas del Multimap
                        for (String user : usu) {
                            for (String password : pass) {
                                if (usuario.getUsuario().equals(user) && usuario.getPassword().equals(password)) {
                                    System.out.println("Usuario logeado");
                                    loggin = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (!loggin) {
                        System.out.println("Usuario o contraseña incorrecta");
                    }
                } else {
                    if (!sizePassword) {
                        // Error semántico
                        return "Error Semántico: password ya fue declarado";
                    }

                    if (!sizeUsuario) {
                        // Error semántico
                        return "Error Semántico: usuario ya fue declarado";
                    }
                }
            } else {
                if (!existePassword) {
                    // Error sintáctico
                    return "Error Sintáctico: password no fue declarado";
                }

                if (!existeUsuario) {
                    // Error sintáctico
                    return "Error Sintáctico: usuario no fue declarado";
                }
            }

        } else {
            // No hay usuarios en la DB
            return "No hay usuarios en la base de datos";

        }

        if (loggin) {
            return "Usuario logeado";
        } else {
            return "Usuario o contraseña incorrecta";
        }
    }

}
