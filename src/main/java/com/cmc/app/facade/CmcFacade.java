/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.facade;

import com.cmc.app.bean.Cmc;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Aldo
 */
@Stateless
public class CmcFacade extends AbstractFacade<Cmc> {

    @PersistenceContext(unitName = "ecmc_PU")
    private EntityManager em;

    public CmcFacade() {
        super(Cmc.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
