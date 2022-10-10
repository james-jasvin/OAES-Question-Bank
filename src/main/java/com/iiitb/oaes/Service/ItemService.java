package com.iiitb.oaes.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iiitb.oaes.Bean.Author;
import com.iiitb.oaes.Bean.Item;
import com.iiitb.oaes.Bean.MCQItem;
import com.iiitb.oaes.Bean.TrueFalseItem;
import com.iiitb.oaes.DAO.Implementation.ProxyItemImpl;
import com.iiitb.oaes.DAO.ItemDao;
import org.json.JSONObject;

import java.util.List;

public class ItemService {
    // For interacting with Items in the DB
    ItemDao itemDao = new ProxyItemImpl();

    // For mapping from JSON string to actual Java objects
    ObjectMapper objectMapper = new ObjectMapper();

    /*
        - Takes in authorId of the author to obtain Items for
        - Calls getItems() method of ProxyItemImpl
        - Returns the items returned by getItems()
    */
    public List<Item> getItems(Integer authorId) {
        List<Item> itemList = itemDao.getItems(authorId);

        // Removing author reference from each item in order to kill cyclic references
        // Note that reference to Course is still retained
        for (Item item: itemList)
            item.setAuthor(null);

        return itemList;
    }

    /*
        - Takes in JSON String input which contains Author data, Item data, ItemType and Course ID of item to be created
        - Converts the JSON String to the Item, Author and courseId (Integer) objects respectively.
        - Calls createItem() method of ItemDao
    */
    public Boolean createItem(String jsonRequest) throws JsonProcessingException {
        JSONObject itemCreationJSON = new JSONObject(jsonRequest);

        Item item = getItemFromJSON(itemCreationJSON);
        Author author = objectMapper.readValue(itemCreationJSON.get("author").toString(), Author.class);
        Integer courseId = (Integer) itemCreationJSON.get("courseId");

        return itemDao.createItem(item, author, courseId);
    }

    /*
        Same flow as createItem()
    */
    public Boolean updateItem(String jsonRequest) throws JsonProcessingException {
        JSONObject itemUpdationJSON = new JSONObject(jsonRequest);

        Item item = getItemFromJSON(itemUpdationJSON);
        Author author = objectMapper.readValue(itemUpdationJSON.get("author").toString(), Author.class);

        return itemDao.updateItem(item, author.getAuthorId());
    }

    /*
        Utility method that takes in JSON string that represents an item and converts it to an object
        of the subclass representing the complete Item, i.e. MCQItem, TrueFalseItem, etc.
    */
    public Item getItemFromJSON(JSONObject itemJSON) throws JsonProcessingException {
        // Class<?> for holding a reference to a class variable of any Class, since we do not know the concrete
        // subclass in advance
        Class<?> itemClass;
        String itemType = "";

        // This is why itemJSON must have itemType as an input parameter from the front-end, otherwise there's no
        // direct way to know what Item is specified in the input JSON, MCQ/True False/etc.
        switch (itemJSON.get("itemType").toString()) {
            case "MCQ":
                itemClass = MCQItem.class;
                itemType = "MCQ";
                break;

            case "TrueFalse":
                itemClass = TrueFalseItem.class;
                itemType = "TrueFalse";
                break;

            default:
                itemClass = MCQItem.class;
        }

        // Convert itemJSON into item object of the relevant class using Jackson ObjectMapper
        Item item = (Item) objectMapper.readValue(itemJSON.get("item").toString(), itemClass);

        // Also important step for the DAO methods
        item.setItemType(itemType);

        return item;
    }

}