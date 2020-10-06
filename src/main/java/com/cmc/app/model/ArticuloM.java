/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.model;

import com.cmc.app.bean.Imagen;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Aldo
 */
public class ArticuloM {
    
    private Integer id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int orden;
    private boolean activo;
    private List<Imagen> imagenes;
    private List<CatalogoM> catalogos;

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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public List<CatalogoM> getCatalogos() {
        return catalogos;
    }

    public void setCatalogos(List<CatalogoM> catalogos) {
        this.catalogos = catalogos;
    }
    
}
