package com.iiitb.oaes.DAO;

import com.iiitb.oaes.Bean.Author;
import com.iiitb.oaes.Bean.Item;

import java.util.List;

public interface ItemDao {
    boolean createItem(Item item, Author author, Integer courseId);
    List<Item> getItems(Author author);
    boolean updateItem(Item item, Author author);
}
