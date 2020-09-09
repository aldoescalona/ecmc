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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aldo
 */
@Entity
@Table(name = "cmc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cmc.findAll", query = "SELECT c FROM Cmc c")
    , @NamedQuery(name = "Cmc.findById", query = "SELECT c FROM Cmc c WHERE c.id = :id")
    , @NamedQuery(name = "Cmc.findByNombre", query = "SELECT c FROM Cmc c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Cmc.findByMaps", query = "SELECT c FROM Cmc c WHERE c.maps = :maps")
    , @NamedQuery(name = "Cmc.findByFacebook", query = "SELECT c FROM Cmc c WHERE c.facebook = :facebook")
    , @NamedQuery(name = "Cmc.findByInstagrama", query = "SELECT c FROM Cmc c WHERE c.instagrama = :instagrama")
    , @NamedQuery(name = "Cmc.findByWhatsapp", query = "SELECT c FROM Cmc c WHERE c.whatsapp = :whatsapp")
    , @NamedQuery(name = "Cmc.findByEmail", query = "SELECT c FROM Cmc c WHERE c.email = :email")
    , @NamedQuery(name = "Cmc.findByTelefono", query = "SELECT c FROM Cmc c WHERE c.telefono = :telefono")
    , @NamedQuery(name = "Cmc.findByDescripcion", query = "SELECT c FROM Cmc c WHERE c.descripcion = :descripcion")})
public class Cmc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "maps")
    private String maps;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "facebook")
    private String facebook;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "instagrama")
    private String instagrama;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "whatsapp")
    private String whatsapp;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Correo electrónico no válido")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "logoId", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Imagen logoId;

    public Cmc() {
    }

    public Cmc(Integer id) {
        this.id = id;
    }

    public Cmc(Integer id, String nombre, String maps, String facebook, String instagrama, String whatsapp, String email, String telefono, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.maps = maps;
        this.facebook = facebook;
        this.instagrama = instagrama;
        this.whatsapp = whatsapp;
        this.email = email;
        this.telefono = telefono;
        this.descripcion = descripcion;
    }

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

    public String getMaps() {
        return maps;
    }

    public void setMaps(String maps) {
        this.maps = maps;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagrama() {
        return instagrama;
    }

    public void setInstagrama(String instagrama) {
        this.instagrama = instagrama;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Imagen getLogoId() {
        return logoId;
    }

    public void setLogoId(Imagen logoId) {
        this.logoId = logoId;
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
        if (!(object instanceof Cmc)) {
            return false;
        }
        Cmc other = (Cmc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cmc.app.bean.Cmc[ id=" + id + " ]";
    }
    
}
