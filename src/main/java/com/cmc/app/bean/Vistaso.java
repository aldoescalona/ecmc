/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.bean;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aldo
 */
@Entity
@Table(name = "vistaso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vistaso.findAll", query = "SELECT v FROM Vistaso v")
    , @NamedQuery(name = "Vistaso.findById", query = "SELECT v FROM Vistaso v WHERE v.id = :id")
    , @NamedQuery(name = "Vistaso.findByContador", query = "SELECT v FROM Vistaso v WHERE v.contador = :contador")
    , @NamedQuery(name = "Vistaso.findByFechaultimo", query = "SELECT v FROM Vistaso v WHERE v.fechaultimo = :fechaultimo")})
public class Vistaso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contador")
    private int contador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaultimo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaultimo;
    @JoinColumn(name = "articuloId", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Articulo articuloId;

    public Vistaso() {
    }

    public Vistaso(Integer id) {
        this.id = id;
    }

    public Vistaso(Integer id, int contador, Date fechaultimo) {
        this.id = id;
        this.contador = contador;
        this.fechaultimo = fechaultimo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public Date getFechaultimo() {
        return fechaultimo;
    }

    public void setFechaultimo(Date fechaultimo) {
        this.fechaultimo = fechaultimo;
    }

    public Articulo getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(Articulo articuloId) {
        this.articuloId = articuloId;
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
        if (!(object instanceof Vistaso)) {
            return false;
        }
        Vistaso other = (Vistaso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cmc.app.bean.Vistaso[ id=" + id + " ]";
    }
    
}
