/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.facade;

import com.cmc.app.bean.ItemCatalogo;
import com.cmc.app.bean.Vistaso;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Aldo
 */
@Stateless
public class VistasoFacade extends AbstractFacade<Vistaso> {

    @PersistenceContext(unitName = "ecmc_PU")
    private EntityManager em;

    public VistasoFacade() {
        super(Vistaso.class);
    }

    public List<Vistaso> getVistasos(Integer articuloId) {

        Query query = getEntityManager().createQuery("SELECT e FROM Vistaso e WHERE e.articuloId.id = :artId");
        query.setParameter("artId", articuloId);

        return query.getResultList();
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
