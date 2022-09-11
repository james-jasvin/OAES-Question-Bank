package com.iiitb.oaes.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iiitb.oaes.Bean.Author;
import com.iiitb.oaes.Bean.Item;
import com.iiitb.oaes.Bean.MCQItem;
import com.iiitb.oaes.Bean.TrueFalseItem;
import com.iiitb.oaes.DAO.Implementation.ItemImpl;
import com.iiitb.oaes.DAO.ItemDao;
import org.json.JSONObject;

import java.util.List;

public class ItemService {
    ItemDao itemDao = new ItemImpl();
    ObjectMapper objectMapper = new ObjectMapper();

    public List<Item> getItems(Integer authorId){
        List<Item> itemList = itemDao.getItems(authorId);

        // Removing author reference from each item in order to kill cyclic references
        // Note that reference to Course is still retained
        for (Item item: itemList)
            item.setAuthor(null);

        return itemList;
    }

    public Boolean createItem(JSONObject itemCreationJSON) throws JsonProcessingException {
        Item item = getItemFromJSON(itemCreationJSON);
        Author author = objectMapper.readValue(itemCreationJSON.get("author").toString(), Author.class);
        Integer courseId = (Integer) itemCreationJSON.get("courseId");

        return itemDao.createItem(item, author, courseId);
    }

    public Boolean updateItem(JSONObject itemUpdationJSON) throws JsonProcessingException {
        Item item = getItemFromJSON(itemUpdationJSON);
        Author author = objectMapper.readValue(itemUpdationJSON.get("author").toString(), Author.class);

        return itemDao.updateItem(item, author.getAuthorId());
    }

    public Item getItemFromJSON(JSONObject itemJSON) throws JsonProcessingException {
        Class<?> itemClass;
        String itemType = "";

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
        item.setItemType(itemType);

        return item;
    }

}