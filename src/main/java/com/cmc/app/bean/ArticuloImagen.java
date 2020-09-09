/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.bean;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aldo
 */
@Entity
@Table(name = "articuloimagen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArticuloImagen.findAll", query = "SELECT a FROM ArticuloImagen a")
    , @NamedQuery(name = "ArticuloImagen.findById", query = "SELECT a FROM ArticuloImagen a WHERE a.id = :id")
    , @NamedQuery(name = "ArticuloImagen.findByOrden", query = "SELECT a FROM ArticuloImagen a WHERE a.orden = :orden")})
public class ArticuloImagen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orden")
    private int orden;
    @JoinColumn(name = "articuloId", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Articulo articuloId;
    @JoinColumn(name = "imagenId", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Imagen imagenId;

    public ArticuloImagen() {
    }

    public ArticuloImagen(Integer id) {
        this.id = id;
    }

    public ArticuloImagen(Integer id, int orden) {
        this.id = id;
        this.orden = orden;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public Articulo getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(Articulo articuloId) {
        this.articuloId = articuloId;
    }

    public Imagen getImagenId() {
        return imagenId;
    }

    public void setImagenId(Imagen imagenId) {
        this.imagenId = imagenId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArticuloImagen)) {
            return false;
        }
        ArticuloImagen other = (ArticuloImagen) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cmc.app.bean.ArticuloImagen[ id=" + id + " ]";
    }
    
}
