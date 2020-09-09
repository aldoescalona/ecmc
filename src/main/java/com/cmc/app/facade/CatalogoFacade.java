/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.facade;

import com.cmc.app.rest.*;
import com.cmc.app.facade.AbstractFacade;
import com.cmc.app.bean.Catalogo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
