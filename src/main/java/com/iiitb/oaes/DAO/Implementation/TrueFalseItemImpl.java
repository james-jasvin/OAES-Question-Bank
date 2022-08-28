package com.iiitb.oaes.DAO.Implementation;

import com.iiitb.oaes.Bean.*;
import com.iiitb.oaes.DAO.AuthorDao;
import com.iiitb.oaes.DAO.CourseDao;
import com.iiitb.oaes.DAO.ItemDao;
import com.iiitb.oaes.utils.SessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TrueFalseItemImpl implements ItemDao {

    @Override
    public boolean createItem(Item item, Author author, Integer courseId) {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            AuthorDao authorImpl = new AuthorImpl();
            CourseDao courseImpl = new CourseImpl();

            Course course = courseImpl.getCourseById(courseId);
            item.setAuthor(author);
            item.setCourse(course);

            session.save(item);
            transaction.commit();
            return true;
        }
        catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public List<Item> getItems(Author author) {
        Session session = SessionUtil.getSession();
        List<Item> items = new ArrayList<>();

        try {
            Query query = session.createQuery("from Item where author=:authorId and itemType='TrueFalse'");
            query.setParameter("authorId", author);

            for (final Object item : query.list())
                items.add((Item) item);

            return items;
        }
        catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean updateItem(Item updatedItem, Author author) {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("from Item where itemId=:itemId and author=:author");
            query.setParameter("itemId", updatedItem.getItemId());
            query.setParameter("author", author);

            if (query.list().size() == 0)
                return false;

            TrueFalseItem dbItem = (TrueFalseItem) query.list().get(0), tfUpdatedItem = (TrueFalseItem) updatedItem;

            if (tfUpdatedItem.getQuestion() != null)
                dbItem.setQuestion(tfUpdatedItem.getQuestion());

            if (tfUpdatedItem.getAnswer() != null)
                dbItem.setAnswer(tfUpdatedItem.getAnswer());

            session.update(dbItem);
            transaction.commit();

            return true;
        }
        catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return false;
        }
    }
}
