package com.app.recursosdb;

public class Usuario {

    private String usuario;
    private String password;
    private String institucion;
    private String fechaCreacion;
    private String fechaModificacion;
    private String nombre;

    public Usuario(String usuario, String password, String nombre, String institucion, String fechaCreacion,
            String fechaModificacion) {


        this.nombre = nombre;


        this.usuario = usuario;
        this.password = password;
        this.institucion = institucion;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;

        System.out.println("usuario: " + usuario);
        System.out.println("nombre: " + nombre);
        System.out.println("password: " + password);
        System.out.println("intitucion: " + institucion);
        
        
        System.out.println(" Usuario creado con exito");

    }

    public Usuario(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }
    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    public String getInstitucion() {
        return institucion;
    }

 
    public void setNombre(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    

    public void setFechaMod(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getFechaCreacion(){
        return this.fechaCreacion;

    }

    public String getFechaMod(){
        return this.fechaModificacion;
    }


}
