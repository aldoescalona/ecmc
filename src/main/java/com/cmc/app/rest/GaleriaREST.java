/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.rest;

import com.cmc.app.bean.Galeria;
import com.cmc.app.bean.Imagen;
import com.cmc.app.model.Respuesta;
import com.cmc.app.security.UsuarioContext;
import com.cmc.app.security.TokenSecured;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Path("galeria")
@Api(value = "Galeria")
public class GaleriaREST {

    @EJB
    private com.cmc.app.facade.GaleriaFacade galeriaFacade;

    @EJB
    private com.cmc.app.facade.ImagenFacade imagenFacade;

    @EJB
    private com.cmc.app.facade.NextIdFacade nextIdFacade;

    @EJB
    private com.cmc.app.facade.ParametroFacade parametroFacade;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Alta Galeria")
    @TokenSecured
    public void create(Galeria entity) {
        galeriaFacade.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @TokenSecured
    public void edit(@PathParam("id") Integer id, Galeria entity) {
        galeriaFacade.edit(entity);
    }

    @DELETE
    @Path("{id}")
    @TokenSecured
    public void remove(@PathParam("id") Integer id) {

        Galeria gaeria = galeriaFacade.find(id);
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

        galeriaFacade.remove(gaeria);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Galeria find(@PathParam("id") Integer id) {
        return galeriaFacade.find(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@TokenSecured
    public List<Galeria> findAll() {
        return galeriaFacade.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Galeria> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return galeriaFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    @TokenSecured
    public String countREST() {
        return String.valueOf(galeriaFacade.count());
    }

    @POST
    @Path("ordenar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Response ordenar(List<Galeria> list) {
        list.stream().map((ent) -> {
            Galeria persistente = galeriaFacade.find(ent.getId());
            persistente.setOrden(ent.getOrden());
            return persistente;
        }).forEach((persistente) -> {
            galeriaFacade.edit(persistente);
        });
        return Response.ok(new Respuesta(true, "OK")).build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Response uploadFiles(@Context HttpServletRequest request, @Context ServletContext context, @Context SecurityContext securityContext) {

        Imagen imagen = null;
        UsuarioContext user = (UsuarioContext) securityContext.getUserPrincipal();

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

                        Integer orden = galeriaFacade.getNextOrden();

                        Galeria galeria = new Galeria();
                        galeria.setId(nextIdFacade.nextIdGaleria());
                        galeria.setImagenId(imagen);
                        galeria.setOrden(orden);
                        galeriaFacade.create(galeria);

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
