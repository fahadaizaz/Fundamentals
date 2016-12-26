package com.aizaz.services;

import com.aizaz.services.model.User;
import com.oneandone.swistec.platform.client.locator.RESTLocator;

import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/A")
@Produces(MediaType.APPLICATION_XML)
public class RestClientImpl {

    private static Logger LOG = LoggerFactory.getLogger(RestClientImpl.class);

    @Path("/resteasyproxy")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getUserProxyA() {

        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/service-b/resources");
        RestService service = target.proxy(RestService.class);

        Response response = service.getUser();
        int statusCode = response.getStatus();
        LOG.info("status code: {}", statusCode);
        User user = response.readEntity(User.class);
        LOG.info("log user firstname: {}, lastname: {}", user.getFirstName(), user.getLastName());

        return Response.ok(user).build();
    }

    /**
     * This method receives a response from rest service based on proxy generated using Platform Client API
     * delivering BaseClientResponse reference back which is the part if jax-rs 1.0 resteasy implementation.
     * This class does not (even) implement readEntity method and thus NotImplementedYetException is thrown.
     */
    @Path("/javaplatformproxy/v1")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getUserProxyBV1() {

        RESTLocator restLocator = new RESTLocator(RestService.class.getName(), "default");
        RestService restServiceProxy = restLocator.locateProxy(RestService.class);

        try {

            Response response = restServiceProxy.getUser();
            LOG.info("Response received of type: {}", response);
            int statusCode = response.getStatus();
            LOG.info("Status code: {}", statusCode);
            User user = response.readEntity(User.class);
            LOG.info("log user firstname: {}, lastname: {}", user.getFirstName(), user.getLastName());
            return Response.status(statusCode).entity(user).build();

        } catch (Exception e) {
            LOG.info("something got really bad: {}", e.getMessage());
            return Response.status(500).entity(e).build();
        }
    }

    @Path("/javaplatformproxy/v2")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getUserProxyBV2() {

        RESTLocator restLocator = new RESTLocator(RestService.class.getName(), "default");
        RestService restServiceProxy = restLocator.locateProxy(RestService.class);

        try {

            ClientResponse response = (ClientResponse) restServiceProxy.getUser();
            LOG.info("Response received of type: {}", response);
            int statusCode = response.getStatus();
            LOG.info("Status code: {}", statusCode);
            User user = (User) response.getEntity(User.class);
            LOG.info("log user firstname: {}, lastname: {}", user.getFirstName(), user.getLastName());
            return Response.status(statusCode).entity(user).build();

        } catch (Exception e) {
            LOG.info("something got really bad: {}", e.getMessage());
            return Response.status(500).entity(e).build();
        }
    }


    @Path("/check")
    @GET
    public Response check() {
        return Response.ok().entity("check success").build();
    }

}
