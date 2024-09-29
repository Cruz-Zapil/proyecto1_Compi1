package com.app.recursosdb.registro;

import com.google.gson.annotations.SerializedName;

public class RegComponent {


     @SerializedName ("ID")
    private String idComponent;
     @SerializedName ("REGISTRO")
    private String registro;
     @SerializedName ("RESPUESTA")
    private String respuesta;

    public RegComponent(String idComponent, String registro, String respuesta) {
        this.idComponent = idComponent;
        this.registro = registro;
        this.respuesta = respuesta;

    }

    public String getIdComponent() {
        return idComponent;
    }

    public void setIdComponent(String idComponent) {
        this.idComponent = idComponent;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
        
}
