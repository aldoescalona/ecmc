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
@Table(name = "galeria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Galeria.findAll", query = "SELECT g FROM Galeria g")
    , @NamedQuery(name = "Galeria.findById", query = "SELECT g FROM Galeria g WHERE g.id = :id")
    , @NamedQuery(name = "Galeria.findByOrden", query = "SELECT g FROM Galeria g WHERE g.orden = :orden")})
public class Galeria implements Serializable {

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
    @JoinColumn(name = "imagenId", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Imagen imagenId;

    public Galeria() {
    }

    public Galeria(Integer id) {
        this.id = id;
    }

    public Galeria(Integer id, int orden) {
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
        if (!(object instanceof Galeria)) {
            return false;
        }
        Galeria other = (Galeria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cmc.app.bean.Galeria[ id=" + id + " ]";
    }
    
}
