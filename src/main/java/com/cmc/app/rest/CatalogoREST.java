/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.rest;

import com.cmc.app.bean.Catalogo;
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
@Path("catalogo")
public class CatalogoREST {

    @EJB
    private com.cmc.app.facade.CatalogoFacade catalogoFacade;
    
    @EJB
    private com.cmc.app.facade.NextIdFacade nextIdFacade;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Response create(Catalogo entity) {
        entity.setId(nextIdFacade.nextIdCatalogo());
        catalogoFacade.create(entity);
        return Response.ok(new Respuesta(true, "OK")).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Response edit(@PathParam("id") Integer id, Catalogo entity) {
        
        Catalogo ent = catalogoFacade.find(id);
        ent.setNombre(entity.getNombre());
        
        catalogoFacade.edit(ent);
        return Response.ok(new Respuesta(true, "OK")).build();
    }

    @DELETE
    @Path("{id}")
    @TokenSecured
    public void remove(@PathParam("id") Integer id) {
        catalogoFacade.remove(catalogoFacade.find(id));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Catalogo find(@PathParam("id") Integer id) {
        return catalogoFacade.find(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public List<Catalogo> findAll() {
        return catalogoFacade.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public List<Catalogo> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return catalogoFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    @TokenSecured
    public String countREST() {
        return String.valueOf(catalogoFacade.count());
    }

}
