/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.model;

import com.cmc.app.bean.ItemCatalogo;
import java.util.List;


/**
 *
 * @author Aldo
 */
public class CatalogoM {
    
    private Integer id;
    private String nombre;
    private List<ModificadorM> modificadores;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ModificadorM> getModificadores() {
        return modificadores;
    }

    public void setModificadores(List<ModificadorM> modificadores) {
        this.modificadores = modificadores;
    }
    
}
