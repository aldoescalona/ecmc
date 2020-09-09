/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.security;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<Exception> {

    public Response toResponse(Exception exception) {
        exception.printStackTrace();
        return Response.status(500).entity("Unfortunately, the application cannot"
                + "process your request at this time.").type("text/plain").build();
    }

}