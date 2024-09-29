package com.app.recursos;

import java.util.ArrayList;

import com.app.recursosdb.registro.Registro;

public class MetodoRegistro {
    

    private ConexionDB db = new ConexionDB();
    private ArrayList <Registro> lista = new ArrayList<Registro>();

    public void obtenerLista(){

        db.recopilarArchivos();
        db.analizarRegistro();
        lista = db.getListRegistro();
        
    }

    public static void main(String[] args) {
            
            MetodoRegistro met = new MetodoRegistro();
            met.obtenerLista();
    }




}
