package com.iiitb.oaes.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iiitb.oaes.Service.AuthorService;
import com.iiitb.oaes.Bean.Author;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("author")
public class AuthorController {
    AuthorService authorService = new AuthorService();

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String jsonRequest) throws JsonProcessingException {
        Author loggedInAuthor = authorService.login(jsonRequest);

        if (loggedInAuthor == null)
            return Response.status(401).entity(null).build();
        else
            return Response.ok().entity(loggedInAuthor).build();
    }
}