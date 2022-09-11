package com.iiitb.oaes.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iiitb.oaes.Bean.Item;
import com.iiitb.oaes.Service.ItemService;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("items")
public class ItemController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthorItems(@QueryParam("authorId") int authorId) {
        List<Item> items = new ItemService().getItems(authorId);
        return Response.ok().entity(items).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createItem(String jsonRequest) throws JsonProcessingException {
        JSONObject itemCreationJSON = new JSONObject(jsonRequest);

        Boolean itemCreation = new ItemService().createItem(itemCreationJSON);

        if (itemCreation)
            return Response.ok().build();
        else
            return Response.status(400).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItem(String jsonRequest) throws JsonProcessingException {
        JSONObject requestedJSON = new JSONObject(jsonRequest);

        Boolean itemUpdation = new ItemService().updateItem(requestedJSON);

        // 204 Status Code to indicate "Resource updated successfully"
        if (itemUpdation)
            return Response.status(204).build();
        else
            return Response.status(400).build();
    }

}