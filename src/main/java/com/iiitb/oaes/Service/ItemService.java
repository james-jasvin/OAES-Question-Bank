package com.iiitb.oaes.Service;

import com.iiitb.oaes.Bean.Author;
import com.iiitb.oaes.Bean.Item;
import com.iiitb.oaes.DAO.Implementation.ItemImpl;
import com.iiitb.oaes.DAO.ItemDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemService {
    ItemDao itemDao = new ItemImpl();

    public List<Item> getItems(Integer authorId){
        List<Item> itemList = itemDao.getItems(authorId);

        // Removing student attribute and receipt attributes from returned list to kill cyclic references
        for (Item item: itemList) {
            item.setAuthor(null);
            item.setCourse(null);
        }

        return itemList;
    }

    public Boolean createItem(Author author, Item item, Integer courseId) {
        return itemDao.createItem(item, author, courseId);
    }

    public Boolean updateItem(Author author, Item item) {
        return itemDao.updateItem(item, author.getAuthorId());
    }
}