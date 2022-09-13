package com.iiitb.oaes.DAO.Implementation;

import com.iiitb.oaes.Bean.Author;
import com.iiitb.oaes.Bean.Item;
import com.iiitb.oaes.DAO.ItemDao;
import com.iiitb.oaes.utils.CachePair;

import org.hibernate.HibernateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProxyItemImpl implements ItemDao {
    ItemDao itemImpl;
    public static HashMap<Integer, CachePair> cacheItems = new HashMap<>();

    public ProxyItemImpl() {
        this.itemImpl = new ItemImpl();
    }

    @Override
    public boolean createItem(Item item, Author author, Integer courseId) {
        cacheItems.getOrDefault(author.getAuthorId(), new CachePair()).setNeedReset(true);
        return itemImpl.createItem(item, author, courseId);
    }

    @Override
    public List<Item> getItems(Integer authorId) {
        if(cacheItems.containsKey(authorId) && !cacheItems.get(authorId).getNeedReset()){
            System.out.println("Found in Cache");
            CachePair pair_items= cacheItems.get(authorId);
            return pair_items.getItems();
        }

        List<Item> items;
        try {
            items = itemImpl.getItems(authorId);
            CachePair fetched_data= new CachePair(items, false);

            cacheItems.put(authorId, fetched_data);
            return items;
        }
        catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean updateItem(Item item, Integer authorId) {
        cacheItems.get(authorId).setNeedReset(true);
        return itemImpl.updateItem(item, authorId);
    }
}
