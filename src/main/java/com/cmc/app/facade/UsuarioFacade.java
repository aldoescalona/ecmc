/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.facade;

import com.cmc.app.bean.Usuario;
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
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "ecmc_PU")
    private EntityManager em;

    public UsuarioFacade() {
        super(Usuario.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Usuario getUsuario(String cuenta, String passs) {
        
        Usuario u = null;
        
        String p = asPassword(passs);

        Query q = getEntityManager().createNamedQuery("Usuario.login");
        q.setParameter(1, cuenta);
        q.setParameter(2, p);

        List<Usuario> list = q.getResultList();
        
        if(list != null && !list.isEmpty()){
            u = list.get(0);
        }
        
        return u;
    }

    
    public String asPassword(String val){
    
        String p = null;
        Query q = em.createNativeQuery("select password(?) as p");
        q.setParameter(1, val);
        Object obj = q.getSingleResult();
       
        if(obj != null && obj instanceof String){
            p = (String)obj;
        }
        
        return p;
    }
}
