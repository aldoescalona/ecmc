package com.cmc.app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Provider
@TokenSecured
@Priority(Priorities.AUTHENTICATION)
public class TokenSecurityFilter implements ContainerRequestFilter {

    @EJB
    private JWTKey app;

  
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        try {

            UsuarioContext ctx = getUsuarioContext(requestContext);

            String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
            System.out.println(" authorizationHeader: " + authorizationHeader);

            if (authorizationHeader == null) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
                return;
            }
            
            String token =  authorizationHeader; 
            
            Jws<Claims> claims = Jwts.parser().setSigningKey(app.getKey()).parseClaimsJws(token);
            Number usuarioId = (Number)claims.getBody().get("id");

            if (ctx == null) {
                ctx = new UsuarioContext();
            }
            
            ctx.setUsuarioId(usuarioId.intValue());

            AppSecurityContext secContext = new AppSecurityContext(ctx, requestContext.getSecurityContext().isSecure());
            requestContext.setSecurityContext(secContext);

        } catch (Exception e) {
            e.printStackTrace();
            requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
        }
    }

    private UsuarioContext getUsuarioContext(ContainerRequestContext requestContext) {
        AppSecurityContext secContext;
        UsuarioContext ctx = null;

        SecurityContext s = requestContext.getSecurityContext();
        if (s instanceof AppSecurityContext) {
            secContext = (AppSecurityContext) s;
            ctx = (UsuarioContext) secContext.getUserPrincipal();
        }

        return ctx;
    }
}
