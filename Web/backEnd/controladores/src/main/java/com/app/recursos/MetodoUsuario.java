package com.app.recursos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.app.gramaticas.Errorx;
import com.app.gramaticas.Reporte;
import com.app.recursos.util.GuardarInfo;
import com.app.recursosdb.Usuario;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class MetodoUsuario {

    private Multimap<String, String> parametros = ArrayListMultimap.create();
    private Multimap<String, String> loginUsuario = ArrayListMultimap.create();
    private String mensaje = "";
    private String nombreUsu="";
    private String idUser="";

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

    public String getRespuesta() {
        return mensaje;
    }
    public String getNombreUsu() {
        return nombreUsu;
    }

    public String getIdUser() {
        return idUser;
    }

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

                                GuardarInfo la = new GuardarInfo();
                                db.getListaUsuario().add(usuarioModificado);
                                la.enListarUsu(db.getListaUsuario());
                                la.agregarUsuario(null);

                                Reporte.agregarMensaje("MODIFICAR_USUARIO", "Usuario modificado correctamente");

                            } else {
                                System.out.println("Error sintactico: se esperaba al menos un parametro a modificar");
                                Errorx error = new Errorx("Sintáctico", "Parametros", "Se esperaba al menos un parametro a modificar", 0, 0);
                                Reporte.agregarError(error);
                            }

                        } else {
                            if (!sizeUsuarioNuevo) {
                                // Error semántico
                                System.out.println("Error Semántico: usuario nuevo ya fue declarado");
                                Errorx error = new Errorx("Semántico", "USUARIO_NUEVO", "Parametro  ya fue Declarado", 0, 0);
                                Reporte.agregarError(error);
                            }
                            if (!sizePasswordNuevo) {
                                // Error semántico
                                System.out.println("Error Semántico: password nuevo ya fue declarado");
                                Errorx error = new Errorx("Semántico", "PASSWORD_NUEVO", "Parametro ya fue Declarado", 0, 0);
                                Reporte.agregarError(error);
                            }
                            if (!sizeInstitucion) {
                                // Error semántico
                                System.out.println("Error Semántico: institucion ya fue declarado");
                                Errorx error = new Errorx("Semántico", "INSTITUCION", "Parametro  ya fue Declarado", 0, 0);
                                Reporte.agregarError(error);

                            }
                            if (!sizeFechaModificacion) {
                                // Error semántico
                                System.out.println("Error Semántico: fecha de modificacion ya fue declarado");
                                Errorx error = new Errorx("Semántico", "FECHA_MODIFICACION", "Parametro ya fue Declarado", 0, 0);
                                Reporte.agregarError(error);
                            }
                        }
                    } else {
                        System.out.println("Usuario no existe");
                        Reporte.agregarMensaje("MODIFICAR_USUARIO", "Usuario no existe");
                    }

                } else {
                    System.out.println("Error Semántico: usuario antiguo ya fue declarado");
                    Errorx error = new Errorx("Semántico", "USUARIO_ANTIGUO", "Parametro ya fue Declarado", 0, 0);
                    Reporte.agregarError(error);
                }

            } else {
                System.out.println("No hay usuarios en la base de datos");

                Reporte.agregarMensaje("MODIFICAR_USUARIO", "No hay usuarios en la base de datos");
                
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
                        GuardarInfo la = new GuardarInfo();
                        la.enListarUsu(db.getListaUsuario());
                        la.agregarUsuario(null);
                        Reporte.agregarMensaje("ELIMINAR_USUARIO", "Usuario eliminado correctamente");
                    } else {
                        System.out.println("Usuario no existe en la base de datos");
                        Reporte.agregarMensaje("ELIMINAR_USUARIO", "Usuario no existe en la base de datos");
                    }
                } else {
                    Errorx error = new Errorx("Semantico", "USUARIO", "Parametro ya fue Declarado", 0, 0);
                    Reporte.agregarError(error);
                }

            } else {
                // Error sintáctico
                Errorx error = new Errorx("Sintactico", "USUARIO", "Parametro no fue Declarado", 0, 0);
                Reporte.agregarError(error);
            }
        } else {
            System.out.println("No hay usuarios en la base de datos");

            Reporte.agregarMensaje("ELIMINAR_USUARIO", "No hay usuarios en la base de datos");

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
                            Reporte.agregarMensaje("NUEVO_USUARIO", "Usuario ya existe cambia de usuario");

                            usuarioExistente = true;
                            break;
                        }
                    }

                    if (!usuarioExistente) {

                        if (!usu.isEmpty() && !pass.isEmpty() && !nom.isEmpty() && !inst.isEmpty()) {

                        Map<String, String> listaUsuario = new HashMap<String, String>();
                        listaUsuario.put("USUARIO", usu);
                        listaUsuario.put("PASSWORD", pass);
                        listaUsuario.put("NOMBRE", nom);
                        listaUsuario.put("INSTITUCION", inst);
                        listaUsuario.put("FECHA_CREACION", fecha);

                        GuardarInfo la = new GuardarInfo();
                        la.enListarUsu(db.getListaUsuario());
                        la.agregarUsuario(listaUsuario);
                        Reporte.agregarMensaje("NUEVO_USUARIO", "Usuario agregado correctamente");

                        }else{
                            System.err.println("Error semantico: faltan parametros obligatorios");
                            Errorx error = new Errorx("Semántico", "Parametros", "Faltan parametros obligatorios", 0, 0);
                            Reporte.agregarError(error);
                        }

                    }

                } else {
                    if (!sizeUsuario) {
                        // Error semántico
                        System.out.println("Error Semántico: usuario ya fue declarado");
                        Errorx error = new Errorx("Semántico", "Usuario", "Parametro Usuario ya fue Declarado", 0, 0);
                        Reporte.agregarError(error);
                    }

                    if (!sizePassword) {
                        // Error semántico
                        System.out.println("Error Semántico: password ya fue declarado");
                        Errorx error = new Errorx("Semántico", "Password", "Parametro Password ya fue Declarado", 0, 0);
                        Reporte.agregarError(error);
                    }

                    if (!sizeNombre) {
                        // Error semántico
                        System.out.println("Error Semántico: nombre ya fue declarado");
                        Errorx error = new Errorx("Semántico", "Nombre", "Parametro Nombre ya fue Declarado", 0, 0);
                        Reporte.agregarError(error);
                    }

                    if (!sizeInstitucion) {
                        // Error semántico
                        System.out.println("Error Semántico: institucion ya fue declarado");
                        Errorx error = new Errorx("Semántico", "Institucion", "Parametro Institucion ya fue Declarado", 0, 0);
                        Reporte.agregarError(error);
                    }

                    if (!sizeFechaCreacion) {
                        // Error semántico
                        System.out.println("Error Semántico: fecha de creacion ya fue declarado");
                        Errorx error = new Errorx("Semántico", "Fecha de Creacion", "Parametro Fecha de Creacion ya fue Declarado", 0, 0);
                        Reporte.agregarError(error);
                    }
                }
            } else {
                if (!existeUsuario) {
                    // Error sintáctico
                    System.out.println("Error Sintáctico: usuario no fue declarado");
                    Errorx error = new Errorx("Sintáctico", "Usuario", "Parametro Usuario no fue Declarado", 0, 0);
                    Reporte.agregarError(error);
                }

                if (!existePassword) {
                    // Error sintáctico
                    System.out.println("Error Sintáctico: password no fue declarado");
                    Errorx error = new Errorx("Sintáctico", "Password", "Parametro Password no fue Declarado", 0, 0);
                    Reporte.agregarError(error);
                }

                if (!existeNombre) {
                    // Error sintáctico
                    System.out.println("Error Sintáctico: nombre no fue declarado");
                    Errorx error = new Errorx("Sintáctico", "Nombre", "Parametro Nombre no fue Declarado", 0, 0);
                    Reporte.agregarError(error);
                }

                if (!existeInstitucion) {
                    // Error sintáctico
                    System.out.println("Error Sintáctico: institucion no fue declarado");
                    Errorx error = new Errorx("Sintáctico", "Institucion", "Parametro Institucion no fue Declarado", 0, 0);
                    Reporte.agregarError(error);
                }
            }
        } else {
            System.out.println("No hay usuarios en la base de datos");

            Reporte.agregarMensaje("NUEVO_USUARIO", "No hay usuarios en la base de datos");

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
            mensaje += "  existe Usuarios en la DataBase\n";

            if (existePassword && existeUsuario) {
                // Ambos parámetros existen
                if (sizePassword && sizeUsuario) {
                    // Analizar si existe en la DB
                    String usu = loginUsuario.get("USUARIO").iterator().next();
                    String pass = loginUsuario.get("PASSWORD").iterator().next();
                    mensaje += "\n\n usu:  " + usu;
                    mensaje += "\n\n pass:" + pass;

                    for (Usuario usuario : db.getListaUsuario()) {
                        // Iterar sobre los usuarios y contraseñas del Multimap

                        if (usuario.getUsuario().equals(usu) && usuario.getPassword().equals(pass)) {
                            System.out.println("Usuario logeado");
                            nombreUsu=usuario.getNombre();
                            idUser=usuario.getUsuario();
                            mensaje += "   <Usuario logeado>\n";
                            loggin = true;
                            break;
                        }

                    }
                    if (!loggin) {
                        System.out.println("Usuario o contraseña incorrecta");
                        mensaje += "<Usuario o contraseña incorrecta>\n";
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
            mensaje += "No hay usuarios en la DB\n";
            return "No hay usuarios en la base de datos";

        }

        if (loggin) {
            mensaje += "Usuario logeado\n";
            return "Usuario logeado";
        } else {
            mensaje += "Usuario o contraseña incorrecta\n";
            return "Usuario o contraseña incorrecta";
        }
    }
}
