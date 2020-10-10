/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.rest;

import com.cmc.app.bean.Articulo;
import com.cmc.app.bean.ArticuloModificador;
import com.cmc.app.bean.Catalogo;
import com.cmc.app.bean.Categoria;
import com.cmc.app.bean.Galeria;
import com.cmc.app.bean.Imagen;
import com.cmc.app.bean.ItemCatalogo;
import com.cmc.app.bean.Vistaso;
import com.cmc.app.model.ArticuloM;
import com.cmc.app.model.CatalogoM;
import com.cmc.app.model.CategoriaM;
import com.cmc.app.model.ModificadorM;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Aldo
 */
@Path("publico")
public class PublicoREST {

    @EJB
    private com.cmc.app.facade.GaleriaFacade galeriaFacade;

    @EJB
    private com.cmc.app.facade.CatalogoFacade catalogoFacade;

    @EJB
    private com.cmc.app.facade.CategoriaFacade categoriaFacade;

    @EJB
    private com.cmc.app.facade.ArticuloFacade articuloFacade;

    @EJB
    private com.cmc.app.facade.ArticuloImagenFacade articuloImagenFacade;
    
    @EJB
    private com.cmc.app.facade.ArticuloModificadorFacade articuloModificadorFacade;

    @EJB
    private com.cmc.app.facade.VistasoFacade vistasoFacade;

    @EJB
    private com.cmc.app.facade.NextIdFacade nextIdFacade;

    @GET
    @Path("articulo/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArticuloM findArticulo(@PathParam("id") Integer id) {

        Articulo articulo = articuloFacade.find(id);

        List<Vistaso> list = vistasoFacade.getVistasos(id);
        if (list.isEmpty()) {
            Vistaso vistaso = new Vistaso();
            vistaso.setArticuloId(articulo);
            vistaso.setContador(1);
            vistaso.setFechaultimo(new Date());
            vistaso.setId(nextIdFacade.nextIdVistaso());
            vistasoFacade.create(vistaso);
        } else {
            Vistaso v = list.get(0);
            v.setContador(v.getContador() + 1);
            vistasoFacade.edit(v);
        }
        
        ArticuloM m = asM(articulo);
        m.setImagenes(getImagenes(articulo.getId()));
        
        Map<Catalogo, List<ArticuloModificador>> map = new HashMap();
        
        List<ArticuloModificador> mods = articuloModificadorFacade.getModificadores(articulo.getId(), null);
        mods.forEach((am) -> {
            List<ArticuloModificador> items = map.get(am.getCatalogoId());
            if(items == null){
                items = new ArrayList();
                map.put(am.getCatalogoId(), items);
            }
            items.add(am);
        });

        List<CatalogoM> cats = new ArrayList();
       
        for(Catalogo cat:map.keySet()){
            CatalogoM catm = asM(cat);
            List<ArticuloModificador> its = map.get(cat);
            List<ModificadorM> modificadores = its.stream().map(i->{
                return asM(i);
            }).collect(Collectors.toList());
            catm.setModificadores(modificadores);
            cats.add(catm);
        }
        m.setCatalogos(cats);
        
        return m;
    }

    @GET
    @Path("galeria")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Galeria> findAll() {
        return galeriaFacade.findAll();
    }

    @GET
    @Path("categorias")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CategoriaM> findAllCategoria() {
        List<Categoria> categorias = categoriaFacade.getCategorias();

        List<CategoriaM> cats = categorias.stream().map(cat -> {
            CategoriaM model = asM(cat);
            model.setArticulos(getArticulos(cat.getId()));
            return model;
        }).collect(Collectors.toList());

        return cats;
    }

    private List<ArticuloM> getArticulos(Integer catId) {
        List<ArticuloM> articulos = articuloFacade.getArticulos(catId).stream().map(ar -> {
            ArticuloM arm = asM(ar);
            arm.setImagenes(getImagenes(ar.getId()));
            return arm;
        }).collect(Collectors.toList());
        return articulos;
    }

    private List<Imagen> getImagenes(Integer artId) {
        List<Imagen> imagenes = articuloImagenFacade.getImagenes(artId).stream().map(ai -> {
            return ai.getImagenId();
        }).collect(Collectors.toList());
        return imagenes;
    }

    private ArticuloM asM(Articulo articulo) {
        ArticuloM m = new ArticuloM();
        m.setId(articulo.getId());
        m.setNombre(articulo.getNombre());
        m.setDescripcion(articulo.getDescripcion());
        m.setOrden(articulo.getOrden());
        m.setPrecio(articulo.getPrecio());
        return m;
    }

    private CategoriaM asM(Categoria categoria) {
        CategoriaM model = new CategoriaM();
        model.setId(categoria.getId());
        model.setNombre(categoria.getNombre());
        model.setDescripcion(categoria.getDescripcion());
        model.setOrden(categoria.getOrden());
        return model;
    }
    
    private CatalogoM asM(Catalogo catalogo){
        CatalogoM model = new CatalogoM();
        model.setId(catalogo.getId());
        model.setNombre(catalogo.getNombre());
        return model;
    }
    
    private ModificadorM asM(ArticuloModificador ar){
        ModificadorM model = new ModificadorM();
        model.setId(ar.getId());        
        model.setNombre(ar.getItemCatalogoId().getNombre());
        model.setTipo(ar.getTipo());
        model.setPrecio(ar.getPrecio());
        return model;
    }
}
