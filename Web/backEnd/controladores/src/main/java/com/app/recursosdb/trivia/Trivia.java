package com.app.recursosdb.trivia;

import java.util.List;

public class Trivia {

    private String id;
    private String nombre;
    private int tiempo_pre;
    private String usuario;
    private String tema;
    private List<Componente> componentes; // lista de componentes
    private String fecha_creacion;

    public Trivia(String id, String nombre, int tiempo_pre, String usuario, String tema,  String fecha,List<Componente> componentes) {
        this.id = id;
        this.nombre = nombre;
        this.tiempo_pre = tiempo_pre;
        this.usuario = usuario;
        this.tema = tema;
        this.fecha_creacion = fecha;
        this.componentes = componentes;

    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }


    public void setComponente(Componente componente) {
        componentes.add(componente);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTiempo_pre() {
        return tiempo_pre;
    }

    public void setTiempo_pre(int tiempo_pre) {
        this.tiempo_pre = tiempo_pre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public List<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<Componente> componentes) {
        this.componentes = componentes;
    }

}
