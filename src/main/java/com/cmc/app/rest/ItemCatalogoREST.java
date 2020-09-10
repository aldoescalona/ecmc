/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.rest;

import com.cmc.app.bean.Catalogo;
import com.cmc.app.bean.ItemCatalogo;
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
@Path("itemcatalogo")
public class ItemCatalogoREST {

    @EJB
    private com.cmc.app.facade.ItemCatalogoFacade itemCatalogoFacade;
    
    @EJB
    private com.cmc.app.facade.CatalogoFacade catalogoFacade;

    @EJB
    private com.cmc.app.facade.NextIdFacade nextIdFacade;
    
    @POST
    @Path("{catalogoId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Response create(@PathParam("catalogoId") Integer catalogoId, ItemCatalogo entity) {
        
        Catalogo cat = catalogoFacade.find(catalogoId);
        
        entity.setId(nextIdFacade.nextIdItemCatalogo());
        entity.setCatalogoId(cat);
        itemCatalogoFacade.create(entity);
        return Response.ok(new Respuesta(true, "OK")).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Response edit(@PathParam("id") Integer id, ItemCatalogo entity) {
        
        ItemCatalogo ent = itemCatalogoFacade.find(id);
        ent.setNombre(entity.getNombre());
        
        itemCatalogoFacade.edit(ent);
        
        return Response.ok(new Respuesta(true, "OK")).build();
    }

    @DELETE
    @Path("{id}")
    @TokenSecured
    public void remove(@PathParam("id") Integer id) {
        itemCatalogoFacade.remove(itemCatalogoFacade.find(id));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public ItemCatalogo find(@PathParam("id") Integer id) {
        return itemCatalogoFacade.find(id);
    }

    @GET
    @Path("elementos/{catalogoId}")
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public List<ItemCatalogo> findAll(@PathParam("catalogoId") Integer catalogoId) {
        return itemCatalogoFacade.getItems(catalogoId);
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public List<ItemCatalogo> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return itemCatalogoFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    @TokenSecured
    public String countREST() {
        return String.valueOf(itemCatalogoFacade.count());
    }

}
