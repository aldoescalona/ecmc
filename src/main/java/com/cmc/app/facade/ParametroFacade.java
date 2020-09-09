/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.facade;

import com.cmc.app.bean.Parametro;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 43700118
 */
@Singleton
public class ParametroFacade extends AbstractFacade<Parametro>{

    @PersistenceContext(unitName = "ecmc_PU")
    private EntityManager em;
    
   private String filePath;

    public ParametroFacade() {
        super(Parametro.class);
    }

    @PostConstruct
    public void init() {
        try {

            Parametro param = super.find("generalFileSystem");
            filePath = param.getValor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFilePath() {
        return filePath;
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
