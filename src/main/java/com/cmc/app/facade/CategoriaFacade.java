/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.facade;

import com.cmc.app.bean.Categoria;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Aldo
 */
@Stateless
public class CategoriaFacade extends AbstractFacade<Categoria> {

    @PersistenceContext(unitName = "ecmc_PU")
    private EntityManager em;

    public CategoriaFacade() {
        super(Categoria.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public Integer getNextOrden() {

        Integer ord = 1;
        Object obj = em.createQuery("select max(e.orden) from Categoria e").getSingleResult();

        if (obj != null && obj instanceof Integer) {
            ord = (Integer) obj;
            ord = ord + 1;
        }

        return ord;
    }
    
}
