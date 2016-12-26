package com.aizaz.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/B")
public interface RestService {

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getUser();

}
