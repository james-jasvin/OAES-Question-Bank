package com.iiitb.oaes.DAO.Implementation;

import com.iiitb.oaes.Bean.Items;
import com.iiitb.oaes.DAO.ItemsDao;
import com.iiitb.oaes.utils.SessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ItemsImpl implements ItemsDao {

    @Override
    public boolean createItem(Items item) {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
            return true;
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public List<Items> getItems() {
        Session session = SessionUtil.getSession();
        List<Items> items = new ArrayList<>();
        try {
            for (final Object item : session.createQuery("from Items").list()) {
                items.add((Items) item);
            }
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
        }
        return items;
    }

    @Override
    public boolean updateItem(Items item) {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("from Items where itemId=:itemId");
            query.setParameter("itemId", item.getItemId());

            for (final Object fetch: query.list())
            {
                Items newObject = (Items) fetch;

                if (item.getQuestion() != null)
                    newObject.setQuestion(item.getQuestion());

                if (item.getOption1() != null)
                    newObject.setOption1(item.getOption1());

                if (item.getOption2() != null)
                    newObject.setOption2(item.getOption2());

                if (item.getOption3() != null)
                    newObject.setOption3(item.getOption3());

                if (item.getOption4() != null)
                    newObject.setOption4(item.getOption4());

                if (item.getAnswer() != null)
                    newObject.setAnswer(item.getAnswer());

                session.update(newObject);
            }
            transaction.commit();
            return true;
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return false;
        }
    }
}
