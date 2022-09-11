//package com.iiitb.oaes.Controller;
//
//import com.iiitb.oaes.Service.AuthorService;
//import com.iiitb.oaes.Bean.Author;
//
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//@Path("author")
//public class AuthorController {
//    AuthorService authorService = new AuthorService();
//
//    @GET
//    @Path("/login")
//    @Produces(MediaType.APPLICATION_JSON) //return type
//    @Consumes(MediaType.APPLICATION_JSON) //parameter
//    public Response login(Author student) {
//        Author loggedInStudent = authorService.login(student);
//
//        if (loggedInStudent == null)
//            return Response.status(401).entity(null).build();
//        else
//            return Response.ok().entity(loggedInStudent).build();
//    }
//}