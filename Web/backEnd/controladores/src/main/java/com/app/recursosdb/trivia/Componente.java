package com.app.recursosdb.trivia;

import com.google.gson.annotations.SerializedName;

public class Componente {

    private static int contadorIndice =1;

    @SerializedName("ID")
    private String idComp;
    @SerializedName("TRIVIA")
    private String trivia;
    @SerializedName("CLASE")
    private String clase;
    @SerializedName("INDICE")
    private int indice;
    @SerializedName("TEXTO_VISIBLE")
    private String textoVisible;
    @SerializedName("RESPUESTA")
    private String respuesta;
    @SerializedName("OPCIONES")
    private String opciones;
    @SerializedName("FILAS")
    private int fila;
    @SerializedName("COLUMNAS")
    private int columna;

    public Componente(String idComp2, String idTriComp, String claseComp, String txtVisComp,
    String resComp, String opcionesComp, int filasComp, int columnasComp){
        
        this.idComp = idComp2;
        this.trivia = idTriComp;
        this.clase = claseComp;
        this.indice = contadorIndice++;
        this.textoVisible = txtVisComp;
        this.respuesta = resComp;
        this.opciones = opcionesComp;
        this.fila = filasComp;
        this.columna = columnasComp;
    }

    public Componente(String idComp2, String idTriComp, String claseComp, int indiceComp, String txtVisComp,
            String resComp, String opcionesComp, int filasComp, int columnasComp) {
        
                this.idComp = idComp2;
                this.trivia = idTriComp;
                this.clase = claseComp;
                this.indice = indiceComp;
                this.textoVisible = txtVisComp;
                this.respuesta = resComp;
                this.opciones = opcionesComp;
                this.fila = filasComp;
                this.columna = columnasComp;

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
