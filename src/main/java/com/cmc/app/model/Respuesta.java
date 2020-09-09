/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.model;

/**
 *
 * @author Aldo
 */
public class Respuesta {
    
    private boolean ok;
    private String mensaje;

    public Respuesta() {
    }
    
    

    public Respuesta(boolean ok, String mensaje) {
        this.ok = ok;
        this.mensaje = mensaje;
    }
    
    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
    
}
