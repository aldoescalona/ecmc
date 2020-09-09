/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.rest;

import com.cmc.app.bean.Usuario;
import com.cmc.app.facade.UsuarioFacade;
import com.cmc.app.model.Credencial;
import com.cmc.app.model.Token;
import com.cmc.app.security.JWTKey;
import com.cmc.app.security.TokenSecured;
import com.cmc.app.security.UsuarioContext;
import javax.ejb.EJB;
import javax.naming.AuthenticationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.apache.log4j.Logger;


/**
 *
 * @author 43700118
 */
@Path("auth")
public class AuthREST {

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private JWTKey jwtkey;
    
    public static int HORAS = 20000;
    private static Logger logger = Logger.getLogger(AuthREST.class);

    public AuthREST() {
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(Credencial cred) {
        try {

            String username = cred.getUsername();
            String password = cred.getPassword();

            Usuario ent = authenticate(username, password);
            
            if(ent == null){
                return Response.status(Response.Status.FORBIDDEN).entity(new Error("Usuario o contraseña incorrectos")).build();
            }
            
            
            String token = jwtkey.token(ent.getId(), username, "admin", HORAS);

            Token r = new Token(token);
            System.out.println(" USR: " + ent );

            Response.ResponseBuilder rb = Response.ok(r);
            Response resp = rb.build();

            return resp;
        } catch (Exception e) {
            logger.error(e, e);
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @POST
    @Path("/renovar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @TokenSecured
    public Response renovarToken(@Context SecurityContext securityContext) {
        try {

            UsuarioContext user = (UsuarioContext) securityContext.getUserPrincipal();

            Usuario ent = usuarioFacade.find(user.getUsuarioId());
            String token = jwtkey.token(ent.getId(), ent.getCuenta(), "admin", HORAS);

            Token r = new Token(token);

            Response.ResponseBuilder rb = Response.ok(r);
            Response resp = rb.build();

            return resp;
        } catch (Exception e) {
            logger.error(e, e);
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
    
    private Usuario authenticate(String username, String password) throws Exception {

        Usuario ent = usuarioFacade.getUsuario(username, password);
        if (ent == null) {
            throw new AuthenticationException();
        }
        return ent;
    }

}
