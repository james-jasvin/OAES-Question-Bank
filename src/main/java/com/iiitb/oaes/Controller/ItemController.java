package com.iiitb.oaes.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iiitb.oaes.Bean.Item;
import com.iiitb.oaes.Service.ItemService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("items")
public class ItemController {
    ItemService itemService = new ItemService();

    /*
        Description:
        Returns all the Items that belong to specified Author.
        Author is specified by query parameter authorId
        Calls getItems() method of ItemService() with the specified authorId
        Returns 200 status with the result Items

        URL: GET /api/items?authorId=1

        Input: Query Parameter, authorId
    */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthorItems(@QueryParam("authorId") int authorId) {
        List<Item> items = itemService.getItems(authorId);
        return Response.ok().entity(items).build();
    }

    /*
        Description:
        Creates the Item attached with the Author under the specified Course.
        All this data (Item, Author & Course) is specified in the input JSON.

        Calls createItem() method of ItemService() with the specified JSON data
        If item creation was successful return 201 status code, otherwise 400 to indicate Bad Request

        URL: POST /api/items

        Input: Item + Author + Course ID + itemType (MCQ/TrueFalse for now)
        {
            "author": {
                "loginId": "jasvin_james",
                "password": "Jasvin@123",
                "authorId": 1
            },
            "item": {
                "question": "Dummy question 5?",
                "option1": "Factory Pattern",
                "option2": "Abstract Factory Pattern",
                "option3": "Singleton Pattern",
                "option4": "Transfer Object Pattern",
                "answer": 3
            },
            "itemType": "MCQ",
            "courseId": 1
        }
    */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createItem(String jsonRequest) throws JsonProcessingException {
        Boolean itemCreation = itemService.createItem(jsonRequest);

        if (itemCreation)
            return Response.status(201).build();
        else
            return Response.status(400).build();
    }

    /*
        Description:
        Updates the specified Item attached with the specified Author.
        The Item & Author data are specified in the input JSON.

        Calls updateItem() method of ItemService() with the specified JSON data
        If item updation was successful return 204 status code (Resource Updated Successfully)
        Otherwise 400 to indicate Bad Request

        If some fields are not to be updated, then keep them NULL.

        URL: PUT /api/items

        Input: Item + Author + itemType (MCQ/TrueFalse for now)
        {
            "author": {
                "loginId": "gaurav_tirodkar",
                "password": "Gaurav@123",
                "authorId": 2
            },
            "item": {
                "itemId": 7,
                "question": "Dummy TF 69?"
            },
            "itemType": "TrueFalse",
        }
    */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItem(String jsonRequest) throws JsonProcessingException {
        Boolean itemUpdation = itemService.updateItem(jsonRequest);

        if (itemUpdation)
            return Response.status(204).build();
        else
            return Response.status(400).build();
    }

}