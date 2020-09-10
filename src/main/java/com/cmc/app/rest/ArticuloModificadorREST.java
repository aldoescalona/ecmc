/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.rest;

import com.cmc.app.bean.Articulo;
import com.cmc.app.bean.ArticuloModificador;
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
@Path("articulomodificador")
public class ArticuloModificadorREST {

    @EJB
    private com.cmc.app.facade.ArticuloModificadorFacade articuloModificadorFacade;

    @EJB
    private com.cmc.app.facade.ArticuloFacade articuloFacade;

    
    @EJB
    private com.cmc.app.facade.NextIdFacade nextIdFacade;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Response create(ArticuloModificador entity) {
        entity.setId(nextIdFacade.nextIdArticuloModificador());
        articuloModificadorFacade.create(entity);
        return Response.ok(new Respuesta(true, "OK")).build();
    }
    
    @POST
    @Path("{articuloId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Response modificadores(List<ArticuloModificador> list, @PathParam("articuloId") Integer articuloId) {
        
        articuloModificadorFacade.clean(articuloId);
        
        Articulo parent = articuloFacade.find(articuloId);
        
        list.stream().map((ent)->{
            ent.setId(nextIdFacade.nextIdArticuloModificador());
            ent.setArticuloId(parent);
            return ent;
        }).forEach((ent) -> {
            articuloModificadorFacade.create(ent);
        });
        
        return Response.ok(new Respuesta(true, "OK")).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @TokenSecured
    public void edit(@PathParam("id") Integer id, ArticuloModificador entity) {
        articuloModificadorFacade.edit(entity);
    }

    @DELETE
    @Path("{id}")
    @TokenSecured
    public void remove(@PathParam("id") Integer id) {
        articuloModificadorFacade.remove(articuloModificadorFacade.find(id));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public ArticuloModificador find(@PathParam("id") Integer id) {
        return articuloModificadorFacade.find(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public List<ArticuloModificador> findAll() {
        return articuloModificadorFacade.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public List<ArticuloModificador> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return articuloModificadorFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    @TokenSecured
    public String countREST() {
        return String.valueOf(articuloModificadorFacade.count());
    }

}
