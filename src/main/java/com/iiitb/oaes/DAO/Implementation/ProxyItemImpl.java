package com.iiitb.oaes.DAO.Implementation;

import com.iiitb.oaes.Bean.Author;
import com.iiitb.oaes.Bean.Item;
import com.iiitb.oaes.DAO.ItemDao;
import com.iiitb.oaes.utils.CachePair;

import org.hibernate.HibernateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
    Proxy Class that mirrors ItemImpl along with caching of Items to quickly respond to getItems() calls

    Cache Structure:
    {
        "authorId": {
            List<Item>: List of Items for the Author,
            Boolean: Whether the cache data is stale or not
         }
    }
*/
public class ProxyItemImpl implements ItemDao {
    // Reference to the actual ItemImpl object
    ItemDao itemImpl;

    // Cache
    public static HashMap<Integer, CachePair> cacheItems = new HashMap<>();

    public ProxyItemImpl() {
        this.itemImpl = new ItemImpl();
    }

    @Override
    public boolean createItem(Item item, Author author, Integer courseId) {
        // Set needReset to true because new item is added, so author's item data will become stale
        cacheItems.getOrDefault(author.getAuthorId(), new CachePair()).setNeedReset(true);
        return itemImpl.createItem(item, author, courseId);
    }

    @Override
    public List<Item> getItems(Integer authorId) {
        // If author's items are cached and is not stale, return cached items
        if (cacheItems.containsKey(authorId) && !cacheItems.get(authorId).getNeedReset()) {
            CachePair pairItems= cacheItems.get(authorId);
            return pairItems.getItems();
        }

        // Otherwise fetch the items from the ItemImpl getItems() method and update it in the cache
        List<Item> items;
        try {
            items = itemImpl.getItems(authorId);
            CachePair fetchedData = new CachePair(items, false);

            cacheItems.put(authorId, fetchedData);
            return items;
        }
        catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean updateItem(Item item, Integer authorId) {
        // Set needReset to true because an update indicates that the cache has become stale
        cacheItems.get(authorId).setNeedReset(true);
        return itemImpl.updateItem(item, authorId);
    }
}
