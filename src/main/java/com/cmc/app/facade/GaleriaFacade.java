/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.facade;

import com.cmc.app.bean.Galeria;
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
public class GaleriaFacade extends AbstractFacade<Galeria> {

    @PersistenceContext(unitName = "ecmc_PU")
    private EntityManager em;

    public GaleriaFacade() {
        super(Galeria.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Integer getNextOrden() {

        Integer ord = 1;
        Object obj = em.createQuery("select max(e.orden) from Galeria e").getSingleResult();

        if (obj != null && obj instanceof Integer) {
            ord = (Integer) obj;
            ord = ord + 1;
        }

        return ord;
    }

    public List<Galeria> findAll() {
        String jql = "Select g from Galeria as g order by g.orden";
        Query sortQuery = getEntityManager().createQuery(jql);
        return sortQuery.getResultList();
    }
}
