/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.rest;

import com.cmc.app.bean.Categoria;
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
@Path("categoria")
public class CategoriaREST {

    @EJB
    private com.cmc.app.facade.CategoriaFacade categoriaFacade;
    
    @EJB
    private com.cmc.app.facade.NextIdFacade nextIdFacade;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Response create(Categoria entity) {
        entity.setId(nextIdFacade.nextIdCategoria());
        entity.setOrden(categoriaFacade.getNextOrden());
        entity.setActivo(Boolean.TRUE);
        categoriaFacade.create(entity);
        return Response.ok(new Respuesta(true, "OK")).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Response edit(@PathParam("id") Integer id, Categoria entity) {
        
        Categoria ent = categoriaFacade.find(id);
        ent.setNombre(entity.getNombre());
        ent.setDescripcion(entity.getDescripcion());
        
        categoriaFacade.edit(ent);
        
        return Response.ok(new Respuesta(true, "OK")).build();
    }
    
    @PUT
    @Path("activar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @TokenSecured
    public void activar(@PathParam("id") Integer id) {
        Categoria ent = categoriaFacade.find(id);
        ent.setActivo(Boolean.TRUE);
        categoriaFacade.edit(ent);
    }
    
    @PUT
    @Path("inactivar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @TokenSecured
    public void inactivar(@PathParam("id") Integer id) {
        Categoria ent = categoriaFacade.find(id);
        ent.setActivo(Boolean.FALSE);
        categoriaFacade.edit(ent);
    }

    @DELETE
    @Path("{id}")
    @TokenSecured
    public void remove(@PathParam("id") Integer id) {
        categoriaFacade.remove(categoriaFacade.find(id));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Categoria find(@PathParam("id") Integer id) {
        return categoriaFacade.find(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public List<Categoria> findAll() {
        return categoriaFacade.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public List<Categoria> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return categoriaFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    @TokenSecured
    public String countREST() {
        return String.valueOf(categoriaFacade.count());
    }

}
