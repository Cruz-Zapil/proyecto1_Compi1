package com.app.recursosdb.trivia;

public class Componente {

    private String idComp;
    private String trivia;
    private String clase;
    private int indice;
    private String textoVisible;
    private String respuesta;
    private String opciones;
    private int fila;
    private int columna;

    public Componente(String idComp, String trivia, String clase,int indice, String textoVisible, String respuesta, String opciones, int fila, int columna) {
        this.idComp = idComp;
        this.trivia = trivia;
        this.clase = clase;
        this.indice = indice;
        this.textoVisible = textoVisible;
        this.respuesta = respuesta;
        this.opciones = opciones;
        this.fila = fila;
        this.columna = columna;
    }

    public String getRespuesta() {
        return respuesta;
    }


    public String getIdComp() {
        return idComp;
    }

    


    public String getClase() {
        return clase;
    }


    public void setClase(String clase) {
        this.clase = clase;
    }


    public int getIndice() {
        return indice;
    }


    public void setIndice(int indice) {
        this.indice = indice;
    }


    public void setIdComp(String idComp) {
        this.idComp = idComp;
    }


    public String getTrivia() {
        return trivia;
    }


    public void setTrivia(String trivia) {
        this.trivia = trivia;
    }



    public String getTextoVisible() {
        return textoVisible;
    }


    public void setTextoVisible(String textoVisible) {
        this.textoVisible = textoVisible;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getOpciones() {
        return opciones;
    }

    public void setOpciones(String opciones) {
        this.opciones = opciones;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int comlumna) {
        this.columna = comlumna;
    }




    
}
