package com.iiitb.oaes.Controller;

import com.iiitb.oaes.Bean.Author;
import com.iiitb.oaes.Bean.Item;
import com.iiitb.oaes.Service.ItemService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("items")
public class ItemController {
    @GET
    @Produces(MediaType.APPLICATION_JSON) //return type
    public Response getAuthorItems(@QueryParam("authorId") int authorId) {
        List<Item> items = new ItemService().getItems(authorId);
        return Response.ok().entity(items).build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createItem(Author author, Item item, Integer courseId) {
        Boolean itemCreation = new ItemService().createItem(author, item, courseId);

        if (itemCreation)
            return Response.ok().build();
        else
            return Response.status(400).build();
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItem(Author author, Item item) {
        Boolean itemUpdation = new ItemService().updateItem(author, item);

        if (itemUpdation)
            return Response.ok().build();
        else
            return Response.status(400).build();
    }

}