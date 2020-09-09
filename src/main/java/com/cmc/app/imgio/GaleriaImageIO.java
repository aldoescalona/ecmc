/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.imgio;

import com.cmc.app.bean.Galeria;
import com.cmc.app.bean.Imagen;
import com.cmc.app.img.AbstractImagenIO;

/**
 *
 * @author Aldo
 */
public class GaleriaImageIO extends AbstractImagenIO<Galeria> {

    public GaleriaImageIO(Galeria entidad) {
        super(entidad);
    }

    @Override
    public Imagen getImagen() {
        return getEntidad().getImagenId();
    }

    @Override
    public void setImagen(Imagen imagen) {
        getEntidad().setImagenId(imagen);
    }
}
