package com.aizaz.services;

import com.aizaz.services.model.User;

import javax.ws.rs.core.Response;

public class RestServiceImpl implements RestService {

    @Override
    public Response getUser() {
        User user = new User("John", "Doe");
        Response response = Response.ok(user).build();
        return response;
    }
}
