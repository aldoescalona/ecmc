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
public class ItemCatalogoFacade extends AbstractFacade<ItemCatalogo> {

    @PersistenceContext(unitName = "ecmc_PU")
    private EntityManager em;

    public ItemCatalogoFacade() {
        super(ItemCatalogo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<ItemCatalogo> getItems(Integer catalogoId) {

        Query query = getEntityManager().createQuery("SELECT e FROM ItemCatalogo e WHERE e.catalogoId.id = :catId");
        query.setParameter("catId", catalogoId);


        return query.getResultList();
    }

    public int getCountItems(Integer catalogoId) {

        Query query = getEntityManager().createQuery("SELECT count(e) FROM ItemCatalogo e WHERE e.catalogoId.id = :catId");
        query.setParameter("catId", catalogoId);


        return ((Long)query.getSingleResult()).intValue();
    }

    public List<ItemCatalogo> getItemsRange(Integer catalogoId, int[] range) {

        Query query = getEntityManager().createQuery("SELECT e FROM ItemCatalogo e WHERE e.catalogoId.id = :catId");
        query.setParameter("catId", catalogoId);

        query.setMaxResults(range[1] - range[0] + 1);
        query.setFirstResult(range[0]);

        return query.getResultList();
    }
    
}
