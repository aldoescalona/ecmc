/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.facade;

import com.cmc.app.bean.Imagen;
import com.cmc.app.img.AbstractImagenIO;
import java.io.File;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Aldo
 */
@Stateless
public class ImagenFacade extends AbstractFacade<Imagen> {

    @PersistenceContext(unitName = "ecmc_PU")
    private EntityManager em;

    @EJB
    private NextIdFacade idFacade;

    public ImagenFacade() {
        super(Imagen.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void create(Imagen imagen, AbstractImagenIO imagenio, File dir) {

        em.persist(imagen);

        Imagen old = imagenio.getImagen();
        imagenio.setImagen(imagen);
        em.merge(imagenio.getEntidad());

        if (old != null) {
            try {

                File fa = new File(dir, old.getImglg());
                File fb = new File(dir, old.getImgmd());
                File fc = new File(dir, old.getImgsm());

                fa.delete();
                fb.delete();
                fc.delete();

                remove(old);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
