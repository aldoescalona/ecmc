package com.cmc.app.security;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class AppSecurityContext implements SecurityContext {

    private UsuarioContext appContext;
    private boolean secure;

    public AppSecurityContext(UsuarioContext appContext, boolean secure) {
        this.appContext = appContext;
        this.secure = secure;
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.FORM_AUTH;
    }

    @Override
    public Principal getUserPrincipal() {
        return appContext;
    }

    @Override
    public boolean isSecure() {
        return secure;
    }

    @Override
    public boolean isUserInRole(String rol) {
        /*if (usuario.getRoles() != null) {
            return usuario.getRoles().contains(rol);
        }
        return false;*/
        
        return true;
    }

}
