package com.iiitb.oaes.DAO.Implementation;

import com.iiitb.oaes.Bean.Authors;
import com.iiitb.oaes.Bean.Items;
import com.iiitb.oaes.DAO.AuthorsDao;
import com.iiitb.oaes.DAO.ItemsDao;
import com.iiitb.oaes.utils.CachePair;
import com.iiitb.oaes.utils.SessionUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProxyItemsImpl implements ItemsDao {
    ItemsImpl itemService;
    public static HashMap<String, CachePair> cacheItems = new HashMap<>();


    public ProxyItemsImpl() {
        this.itemService = new ItemsImpl();
    }

    @Override
    public boolean createItem(Items item, String loginId, String password, Integer courseId) {
        return itemService.createItem(item,loginId,password,courseId);
    }

    @Override
    public List<Items> getItems(String loginId, String password) {
        Session session = SessionUtil.getSession();

        if(cacheItems.containsKey(loginId) &&  cacheItems.get(loginId).getNeedReset() == false){
//            System.out.println("Found in Cache");
            CachePair pair_items= cacheItems.get(loginId);
            return pair_items.getItems();
        }

        List<Items> items = new ArrayList<>();

        try {
            AuthorsDao authorImpl = new AuthorsImpl();

            Authors author = authorImpl.loginAuthor(loginId, password);
            if (author == null)
                return new ArrayList<>();

            Query query = session.createQuery("from Items where author=:authorId");
            query.setParameter("authorId", author);

            for (final Object item : query.list()) {
                items.add((Items) item);
            }
            CachePair fetched_data= new CachePair(items, false);

            cacheItems.put(loginId,fetched_data);
            return items;
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean updateItem(Items item, String loginId, String password) {
        cacheItems.get(loginId).setNeedReset(true);
        return itemService.updateItem(item,loginId,password);
    }
}
