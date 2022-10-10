package com.iiitb.oaes.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iiitb.oaes.Service.AuthorService;
import com.iiitb.oaes.Bean.Author;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("author")
public class AuthorController {
    AuthorService authorService = new AuthorService();

    /*
        URL: POST /api/author/login

        Input: Login User Data
        {
            "loginId": "jasvin_james",
            "password": "Jasvin@123"
        }

        Description:
        Authenticates login of the specified Author and returns 401 if failed and 200 along with the Author object
        if successful.

        Passes JSON data as string to the login() method of AuthorService which returns the Author DB object as response
        If response is NULL it means that login failed due to authentication, so return 401 Status code
        Otherwise return 200 status code with the logged in Author object as the response
     */
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