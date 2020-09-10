/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.facade;

import com.cmc.app.bean.Catalogo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Aldo
 */
@Stateless
public class CatalogoFacade extends AbstractFacade<Catalogo> {

    @PersistenceContext(unitName = "ecmc_PU")
    private EntityManager em;

    public CatalogoFacade() {
        super(Catalogo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
