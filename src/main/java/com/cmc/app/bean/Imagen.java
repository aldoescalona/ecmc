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
import javax.persistence.Id;
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
@Table(name = "imagen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Imagen.findAll", query = "SELECT i FROM Imagen i")
    , @NamedQuery(name = "Imagen.findById", query = "SELECT i FROM Imagen i WHERE i.id = :id")})
public class Imagen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "imglg")
    private String imglg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "imgmd")
    private String imgmd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "imgsm")
    private String imgsm;
    

    public Imagen() {
    }

    public Imagen(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImglg() {
        return imglg;
    }

    public void setImglg(String imglg) {
        this.imglg = imglg;
    }

    public String getImgmd() {
        return imgmd;
    }

    public void setImgmd(String imgmd) {
        this.imgmd = imgmd;
    }

    public String getImgsm() {
        return imgsm;
    }

    public void setImgsm(String imgsm) {
        this.imgsm = imgsm;
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
        if (!(object instanceof Imagen)) {
            return false;
        }
        Imagen other = (Imagen) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cmc.app.bean.Imagen[ id=" + id + " ]";
    }
    
}
