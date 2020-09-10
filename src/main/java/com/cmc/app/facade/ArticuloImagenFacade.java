/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.facade;

import com.cmc.app.bean.Articulo;
import com.cmc.app.bean.ArticuloImagen;
import com.cmc.app.bean.ItemCatalogo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Aldo
 */
@Stateless
public class ArticuloImagenFacade extends AbstractFacade<ArticuloImagen> {

    @PersistenceContext(unitName = "ecmc_PU")
    private EntityManager em;

    public ArticuloImagenFacade() {
        super(ArticuloImagen.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public Integer getNextOrden(Articulo articulo) {

        Integer ord = 1;
        Query query = em.createQuery("select max(e.orden) from ArticuloImagen e WHERE e.articuloId = :articuloId");
        query.setParameter("articuloId", articulo);
        Object obj = query.getSingleResult();
        
        
        if (obj != null && obj instanceof Integer) {
            ord = (Integer) obj;
            ord = ord + 1;
        }

        return ord;
    }
    
    
    public List<ArticuloImagen> getImagenes(Integer articuloId) {

        Query query = getEntityManager().createQuery("SELECT e FROM ArticuloImagen e WHERE e.articuloId.id = :artId");
        query.setParameter("artId", articuloId);

        return query.getResultList();
    }
}
