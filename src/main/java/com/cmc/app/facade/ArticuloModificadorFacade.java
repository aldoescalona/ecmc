/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.facade;

import com.cmc.app.bean.ArticuloModificador;
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
public class ArticuloModificadorFacade extends AbstractFacade<ArticuloModificador> {

    @PersistenceContext(unitName = "ecmc_PU")
    private EntityManager em;

    public ArticuloModificadorFacade() {
        super(ArticuloModificador.class);
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public void clean(Integer articuloId){
        Query q = getEntityManager().createQuery("DELETE from ArticuloModificador e WHERE e.articuloId.id = :artId");
        q.setParameter("artId", articuloId);
        
        q.executeUpdate();
    }
    
     public List<ArticuloModificador> getModificadores(Integer articuloId) {

        Query query = getEntityManager().createQuery("SELECT e FROM ArticuloModificador e WHERE e.articuloId.id = :artId");
        query.setParameter("artId", articuloId);
        return query.getResultList();
    }
    
    
}
