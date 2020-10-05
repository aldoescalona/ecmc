/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.facade;

import com.cmc.app.bean.Articulo;
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
public class ArticuloFacade extends AbstractFacade<Articulo> {

    @PersistenceContext(unitName = "ecmc_PU")
    private EntityManager em;

    public ArticuloFacade() {
        super(Articulo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
     public List<Articulo> getArticulos(Integer categoriaId) {

        Query query = getEntityManager().createQuery("SELECT e FROM Articulo e WHERE e.categoriaId.id = :catId AND e.activo = true ORDER BY e.orden");
        query.setParameter("catId", categoriaId);

        return query.getResultList();
    }
}
