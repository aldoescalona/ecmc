/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.conf;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Aldo
 */
@javax.ws.rs.ApplicationPath("v1")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }
    
    @Override
    public Set<Object> getSingletons() {
        Set<Object> set = new HashSet();
        set.add(new JacksonJsonProvider(newObjectMapper()));
        return set;
    }
    
    private static ObjectMapper newObjectMapper() {
        System.out.println(" CONFIGURAR ObjectMapper   \t[OK]");
        ObjectMapper m = new ObjectMapper();
         
         m.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        m.configure(SerializationFeature.INDENT_OUTPUT, true); // Different from default so you can test it :)
        m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        m.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        
        // Customization goes here...
        return m;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.cmc.app.conf.CrossOriginResourceSharingFilter.class);
        resources.add(com.cmc.app.rest.ArticuloImagenREST.class);
        resources.add(com.cmc.app.rest.ArticuloModificadorREST.class);
        resources.add(com.cmc.app.rest.ArticuloREST.class);
        resources.add(com.cmc.app.rest.AuthREST.class);
        resources.add(com.cmc.app.rest.CatalogoREST.class);
        resources.add(com.cmc.app.rest.CategoriaREST.class);
        resources.add(com.cmc.app.rest.CmcREST.class);
        resources.add(com.cmc.app.rest.GaleriaREST.class);
        resources.add(com.cmc.app.rest.ImagenREST.class);
        resources.add(com.cmc.app.rest.ItemCatalogoREST.class);
        resources.add(com.cmc.app.rest.MensajeREST.class);
        resources.add(com.cmc.app.rest.UsuarioREST.class);
        resources.add(com.cmc.app.security.CustomExceptionMapper.class);
        resources.add(com.cmc.app.security.TokenSecurityFilter.class);
    }
    
}
