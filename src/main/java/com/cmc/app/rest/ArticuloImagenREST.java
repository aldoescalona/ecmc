/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.rest;

import com.cmc.app.bean.Articulo;
import com.cmc.app.bean.ArticuloImagen;
import com.cmc.app.bean.Galeria;
import com.cmc.app.bean.Imagen;
import com.cmc.app.model.Respuesta;
import com.cmc.app.security.TokenSecured;
import com.cmc.app.security.UsuarioContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Aldo
 */
@Path("articuloimagen")
public class ArticuloImagenREST {

    @EJB
    private com.cmc.app.facade.ArticuloImagenFacade articuloImagenFacade;
    
    @EJB
    private com.cmc.app.facade.ArticuloFacade articuloFacade;

    @EJB
    private com.cmc.app.facade.ImagenFacade imagenFacade;

    @EJB
    private com.cmc.app.facade.NextIdFacade nextIdFacade;
    
    @EJB
    private com.cmc.app.facade.ParametroFacade parametroFacade;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Response create(ArticuloImagen entity) {
        articuloImagenFacade.create(entity);
        return Response.ok(new Respuesta(true, "OK")).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @TokenSecured
    public void edit(@PathParam("id") Integer id, ArticuloImagen entity) {
        articuloImagenFacade.edit(entity);
    }

    @DELETE
    @Path("{id}")
    @TokenSecured
    public void remove(@PathParam("id") Integer id) {
        
        ArticuloImagen gaeria = articuloImagenFacade.find(id);
        Imagen old = gaeria.getImagenId();

        File dir = new File(parametroFacade.getFilePath());
        
        if (old != null) {
            try {

                File fa = new File(dir, old.getImglg());
                File fb = new File(dir, old.getImgmd());
                File fc = new File(dir, old.getImgsm());

                fa.delete();
                fb.delete();
                fc.delete();

                imagenFacade.remove(old);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        articuloImagenFacade.remove(gaeria);
        
        // articuloImagenFacade.remove(articuloImagenFacade.find(id));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public ArticuloImagen find(@PathParam("id") Integer id) {
        return articuloImagenFacade.find(id);
    }

    @GET
    @Path("imagenes/{articuloId}")
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public List<ArticuloImagen> findAll(@PathParam("articuloId") Integer articuloId) {
        return articuloImagenFacade.getImagenes(articuloId);
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public List<ArticuloImagen> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return articuloImagenFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    @TokenSecured
    public String countREST() {
        return String.valueOf(articuloImagenFacade.count());
    }
    
    @POST
    @Path("ordenar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Response ordenar(List<ArticuloImagen> list) {
        list.stream().map((ent) -> {
            ArticuloImagen persistente = articuloImagenFacade.find(ent.getId());
            persistente.setOrden(ent.getOrden());
            return persistente;
        }).forEachOrdered((persistente) -> {
            articuloImagenFacade.edit(persistente);
        });
        return Response.ok(new Respuesta(true, "OK")).build();
    }

    
    @POST
    @Path("/upload/{articuloId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Response uploadFiles(@PathParam("articuloId") Integer articuloId, @Context HttpServletRequest request, @Context ServletContext context, @Context SecurityContext securityContext) {

        Imagen imagen = null;
        UsuarioContext user = (UsuarioContext) securityContext.getUserPrincipal();
        
        Articulo articulo = articuloFacade.find(articuloId);

        File dir = new File(parametroFacade.getFilePath());

        if (ServletFileUpload.isMultipartContent(request)) {
            final FileItemFactory factory = new DiskFileItemFactory();
            final ServletFileUpload fileUpload = new ServletFileUpload(factory);
            try {

                FileItemIterator iter = fileUpload.getItemIterator(request);

                if (!dir.exists()) {
                    dir.mkdirs();
                }

                while (iter.hasNext()) {
                    FileItemStream item = iter.next();

                    InputStream stream = item.openStream();

                    if (item.isFormField()) {
                        // System.out.println("Field Name: " + fieldName + ", andidate Name: " + Streams.asString(stream));
                    } else {

                        String uid = UUID.randomUUID().toString();
                        String lg = String.format("%s/%s_lg.png", "cmc", uid);
                        String md = String.format("%s/%s_md.png", "cmc", uid);
                        String sm = String.format("%s/%s_sm.png", "cmc", uid);

                        File destinolg = new File(dir, lg);
                        File destinomd = new File(dir, md);
                        File destinosm = new File(dir, sm);

                        OutputStream out = new FileOutputStream(destinolg);
                        byte[] buf = new byte[1024];
                        int len;

                        while ((len = stream.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }

                        stream.close();
                        out.close();

                        Thumbnails.of(destinolg).size(360, 360).outputQuality(0.75).toFile(destinomd);
                        Thumbnails.of(destinolg).size(150, 150).outputQuality(0.75).toFile(destinosm);

                        imagen = new Imagen();
                        imagen.setId(nextIdFacade.nextIdImagen());
                        imagen.setImglg(lg);
                        imagen.setImgmd(md);
                        imagen.setImgsm(sm);
                        imagenFacade.create(imagen);

                        Integer orden = articuloImagenFacade.getNextOrden(articulo);

                        ArticuloImagen galeria = new ArticuloImagen();
                        galeria.setId(nextIdFacade.nextIdArticuloImagen());
                        galeria.setImagenId(imagen);
                        galeria.setArticuloId(articulo);
                        galeria.setOrden(orden);
                        articuloImagenFacade.create(galeria);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }

        if (imagen == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(imagen).build();
    }
    
    
    
}
