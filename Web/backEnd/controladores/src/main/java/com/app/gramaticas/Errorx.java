package com.app.gramaticas;


public class Errorx {
    private String tipo;
    private String descripcion;
    private String lexema;
    private int linea;
    private int columna;


    public Errorx(String tipo,String lexema , String descripcion, int linea, int columna) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.linea = linea;
        this.columna = columna;
        this.lexema = lexema;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    // Getters y Setters
    public String getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public int getLinea() { return linea; }
    public int getColumna() { return columna; }

    @Override
    public String toString() {
        return tipo + " Error: " + descripcion + " en línea: " + linea + ", columna: " + columna;
    }

    
}
