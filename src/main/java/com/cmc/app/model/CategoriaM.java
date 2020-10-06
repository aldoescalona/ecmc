/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.model;

import java.util.List;

/**
 *
 * @author Aldo
 */
public class CategoriaM {
    
    private Integer id;
    private String nombre;
    private String descripcion;
    private int orden;
    
    private List<ArticuloM> articulos;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public List<ArticuloM> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<ArticuloM> articulos) {
        this.articulos = articulos;
    }
    
    
}
