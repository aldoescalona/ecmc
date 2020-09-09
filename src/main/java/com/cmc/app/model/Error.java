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
public class Error {
    
    private String mensaje; 

    public Error(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
