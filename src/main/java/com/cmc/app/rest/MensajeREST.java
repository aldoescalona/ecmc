/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.rest;

import com.cmc.app.bean.Mensaje;
import com.cmc.app.model.Respuesta;
import com.cmc.app.security.TokenSecured;
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
import javax.ws.rs.core.Response;

/**
 *
 * @author Aldo
 */
@Path("mensaje")
public class MensajeREST {

    @EJB
    private com.cmc.app.facade.MensajeFacade mensajeFacade;
    
    @EJB
    private com.cmc.app.facade.NextIdFacade nextIdFacade;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Mensaje entity) {
        entity.setId(nextIdFacade.nextIdMensaje());
        mensajeFacade.create(entity);
        return Response.ok(new Respuesta(true, "OK")).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @TokenSecured
    public void edit(@PathParam("id") Integer id, Mensaje entity) {
        mensajeFacade.edit(entity);
    }

    @DELETE
    @Path("{id}")
    @TokenSecured
    public void remove(@PathParam("id") Integer id) {
        mensajeFacade.remove(mensajeFacade.find(id));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Mensaje find(@PathParam("id") Integer id) {
        return mensajeFacade.find(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public List<Mensaje> findAll() {
        return mensajeFacade.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public List<Mensaje> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return mensajeFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    @TokenSecured
    public String countREST() {
        return String.valueOf(mensajeFacade.count());
    }

}
