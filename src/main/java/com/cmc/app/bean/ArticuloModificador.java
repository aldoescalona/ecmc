/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.bean;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "articulomodificador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArticuloModificador.findAll", query = "SELECT a FROM ArticuloModificador a")
    , @NamedQuery(name = "ArticuloModificador.findById", query = "SELECT a FROM ArticuloModificador a WHERE a.id = :id")
    , @NamedQuery(name = "ArticuloModificador.findByTipo", query = "SELECT a FROM ArticuloModificador a WHERE a.tipo = :tipo")
    , @NamedQuery(name = "ArticuloModificador.findByPrecio", query = "SELECT a FROM ArticuloModificador a WHERE a.precio = :precio")})
public class ArticuloModificador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo")
    private int tipo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    private BigDecimal precio;
    @JoinColumn(name = "articuloId", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Articulo articuloId;
    @JoinColumn(name = "catalogoId", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Catalogo catalogoId;
    @JoinColumn(name = "itemCatalogoId", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private ItemCatalogo itemCatalogoId;

    public ArticuloModificador() {
    }

    public ArticuloModificador(Integer id) {
        this.id = id;
    }

    public ArticuloModificador(Integer id, int tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Articulo getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(Articulo articuloId) {
        this.articuloId = articuloId;
    }

    public Catalogo getCatalogoId() {
        return catalogoId;
    }

    public void setCatalogoId(Catalogo catalogoId) {
        this.catalogoId = catalogoId;
    }

    public ItemCatalogo getItemCatalogoId() {
        return itemCatalogoId;
    }

    public void setItemCatalogoId(ItemCatalogo itemCatalogoId) {
        this.itemCatalogoId = itemCatalogoId;
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
        if (!(object instanceof ArticuloModificador)) {
            return false;
        }
        ArticuloModificador other = (ArticuloModificador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cmc.app.bean.ArticuloModificador[ id=" + id + " ]";
    }
    
}
