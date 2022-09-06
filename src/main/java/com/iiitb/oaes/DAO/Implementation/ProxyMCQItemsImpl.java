package com.iiitb.oaes.DAO.Implementation;

import com.iiitb.oaes.Bean.Author;
import com.iiitb.oaes.Bean.Item;
import com.iiitb.oaes.DAO.ItemDao;
import com.iiitb.oaes.utils.CachePair;
import com.iiitb.oaes.utils.SessionUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProxyMCQItemsImpl implements ItemDao {
    ItemDao itemImpl;
    public static HashMap<String, CachePair> cacheItems = new HashMap<>();


    public ProxyMCQItemsImpl() {
        this.itemImpl = new MCQItemImpl();
    }

    @Override
    public boolean createItem(Item item, Author author, Integer courseId) {
        return itemImpl.createItem(item,author,courseId);
    }

    @Override
    public List<Item> getItems(Author author) {
        String loginId = author.getLoginId();

        if(cacheItems.containsKey(loginId) && !cacheItems.get(loginId).getNeedReset()){
            System.out.println("Found in Cache");
            CachePair pair_items= cacheItems.get(loginId);
            return pair_items.getItems();
        }

        List<Item> items;
        try {
            items = itemImpl.getItems(author);
            CachePair fetched_data= new CachePair(items, false);

            cacheItems.put(loginId,fetched_data);
            return items;
        }
        catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean updateItem(Item item, Author author) {
        String loginId = author.getLoginId();

        cacheItems.get(loginId).setNeedReset(true);
        return itemImpl.updateItem(item,author);
    }
}
