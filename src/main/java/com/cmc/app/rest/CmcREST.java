/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.rest;

import com.cmc.app.bean.Cmc;
import java.util.List;
import javax.ejb.EJB;
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
@Path("cmc")
public class CmcREST {

    @EJB
    private com.cmc.app.facade.CmcFacade cmcFacade;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Cmc entity) {
        cmcFacade.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("id") Integer id, Cmc entity) {
        cmcFacade.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        cmcFacade.remove(cmcFacade.find(id));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Cmc find(@PathParam("id") Integer id) {
        return cmcFacade.find(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cmc> findAll() {
        return cmcFacade.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cmc> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return cmcFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(cmcFacade.count());
    }

}
