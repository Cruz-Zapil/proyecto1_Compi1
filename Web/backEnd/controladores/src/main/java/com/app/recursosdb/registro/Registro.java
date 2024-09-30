package com.app.recursosdb.registro;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Registro {

    @SerializedName("ID_TRIVIA")
    private String idTrivia;
    @SerializedName("USUARIO")
    private String usuario;
    @SerializedName("TIEMPO")
    private int tiempo;
    @SerializedName("RESPUESTAS")
    private List<RegComponent> listRegistroC;
    @SerializedName("PUNTEO")
    private int punteo;

    public Registro(String idTrivia, String usuario, int tiempo ,int punteo,List<RegComponent> listRegistroC) {
        this.idTrivia = idTrivia;
        this.usuario = usuario;
        this.listRegistroC = listRegistroC;
        this.punteo = punteo;
        this.tiempo = tiempo;
    }

    public void setRespuesta(RegComponent componente) {
        if (listRegistroC == null) {
            listRegistroC = new ArrayList<>();
        }
        listRegistroC.add(componente);
    }

    public String getIdTrivia() {
        return idTrivia;
    }

    public void setIdTrivia(String idTrivia) {
        this.idTrivia = idTrivia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<RegComponent> getListRegistroC() {
        return listRegistroC;
    }

    public void setListRegistroC(List<RegComponent> listRegistroC) {
        this.listRegistroC = listRegistroC;
    }

    public int getPunteo() {
        return punteo;
    }

    public void setPunteo(int punteo) {
        this.punteo = punteo;
    }

    @Override
    public String toString() {
        return "Registro [idTrivia=" + idTrivia + ", usuario=" + usuario + ", listRegistroC=" + listRegistroC
                + ", punteo=" + punteo + "]";
    }

    public int getTiempo() {
        return tiempo;
    }

    

}
