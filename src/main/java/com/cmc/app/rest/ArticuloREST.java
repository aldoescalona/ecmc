/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.rest;

import com.cmc.app.bean.Articulo;
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
@Path("articulo")
public class ArticuloREST {

    @EJB
    private com.cmc.app.facade.ArticuloFacade articuloFacade;
    
    @EJB
    private com.cmc.app.facade.CategoriaFacade categoriaFacade;

    @EJB
    private com.cmc.app.facade.NextIdFacade nextIdFacade;
    
    @POST
    @Path("{categoriaId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Response create(@PathParam("categoriaId") Integer categoriaId, Articulo entity) {
        
        Categoria cat = categoriaFacade.find(categoriaId);
        
        entity.setId(nextIdFacade.nextIdArticulo());
        entity.setCategoriaId(cat);
        articuloFacade.create(entity);
        return Response.ok(new Respuesta(true, "OK")).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Response edit(@PathParam("id") Integer id, Articulo entity) {
        
        Articulo ent = articuloFacade.find(id);
        ent.setNombre(entity.getNombre());
        ent.setDescripcion(entity.getDescripcion());
        ent.setPrecio(entity.getPrecio());
        
        articuloFacade.edit(ent);
        
        return Response.ok(new Respuesta(true, "OK")).build();
    }

    @DELETE
    @Path("{id}")
    @TokenSecured
    public void remove(@PathParam("id") Integer id) {
        articuloFacade.remove(articuloFacade.find(id));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Articulo find(@PathParam("id") Integer id) {
        return articuloFacade.find(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public List<Articulo> findAll() {
        return articuloFacade.getArticulos(true);
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public List<Articulo> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return articuloFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("{categoriaId}/{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public List<Articulo> findArticulosRange(@PathParam("categoriaId") Integer categoriaId,
                                             @PathParam("from") Integer from, @PathParam("to") Integer to) {
        return articuloFacade.getArticulos(categoriaId, new int[]{from, to});
    }


    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    @TokenSecured
    public String countREST() {
        return String.valueOf(articuloFacade.count());
    }

    @GET
    @Path("{categoriaId}/count")
    @Produces(MediaType.TEXT_PLAIN)
    @TokenSecured
    public String countArticulosREST(@PathParam("categoriaId") Integer categoriaId) {
        return String.valueOf(articuloFacade.getCountArticulos(categoriaId));
    }
    
    @PUT
    @Path("activar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @TokenSecured
    public void activar(@PathParam("id") Integer id) {
        Articulo ent = articuloFacade.find(id);
        ent.setActivo(Boolean.TRUE);
        articuloFacade.edit(ent);
    }
    
    @PUT
    @Path("inactivar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @TokenSecured
    public void inactivar(@PathParam("id") Integer id) {
        Articulo ent = articuloFacade.find(id);
        ent.setActivo(Boolean.FALSE);
        articuloFacade.edit(ent);
    }

    
    @POST
    @Path("ordenar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Response ordenar(List<Articulo> list) {
        list.stream().map((ent) -> {
            Articulo persistente = articuloFacade.find(ent.getId());
            persistente.setOrden(ent.getOrden());
            return persistente;
        }).forEachOrdered((persistente) -> {
            articuloFacade.edit(persistente);
        });
        return Response.ok(new Respuesta(true, "OK")).build();

    }
}
