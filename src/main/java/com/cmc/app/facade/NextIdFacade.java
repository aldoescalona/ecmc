/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Aldo
 */
@Stateless
public class NextIdFacade {
    
    @PersistenceContext(unitName = "ecmc_PU")
    private EntityManager em;
    
    
    public Integer nextId(String key){
    
        Integer id = null;
        Query q = em.createNativeQuery("select NextVal(?) as id");
        q.setParameter(1, key);
        Object obj = q.getSingleResult();
       
        if(obj != null && obj instanceof Integer){
            id = (Integer)obj;
        }
        
        return id;
    }
    
    public Integer nextIdGaleria(){
        return nextId("galeria_id_seq");
    }
    
    public Integer nextIdImagen(){
        return nextId("imagen_id_seq");
    }
    
    public Integer nextIdCategoria(){
        return nextId("categoria_id_seq");
    }
    
    public Integer nextIdArticulo(){
        return nextId("articulo_id_seq");
    }
    
    public Integer nextIdArticuloImagen(){
        return nextId("articuloimagen_id_seq");
    }
    
    public Integer nextIdArticuloModificador(){
        return nextId("articulomodificador_id_seq");
    }
    
    public Integer nextIdItemCatalogo(){
        return nextId("itemcatalogo_id_seq");
    }
    
    public Integer nextIdMensaje(){
        return nextId("mensaje_id_seq");
    }
    
    public Integer nextIdUsuario(){
        return nextId("usuario_id_seq");
    }
    
    public Integer nextIdVistaso(){
        return nextId("vistaso_id_seq");
    }
    
    public Integer nextIdCatalogo(){
        return nextId("catalogo_id_seq");
    }
    
}
