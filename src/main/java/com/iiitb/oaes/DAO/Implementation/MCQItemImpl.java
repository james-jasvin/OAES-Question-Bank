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

public class MCQItemImpl implements ItemDao {
    @Override
    public boolean createItem(Item item, Author author, Integer courseId) {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

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
            // Fetch the Author's MCQ Items
            Query query = session.createQuery("from Item where author=:author and itemType='MCQ'");
            query.setParameter("author", author);

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

            Item dbItem = (Item) query.list().get(0);

            if (updatedItem.getQuestion() != null)
                dbItem.setQuestion(updatedItem.getQuestion());

            if (dbItem.getItemType().equals("MCQ")) {
                MCQItem mcqUpdatedItem = (MCQItem) updatedItem, mcqDbItem = (MCQItem) dbItem;
                updateMCQItem(mcqUpdatedItem, mcqDbItem);
            }
            else if (dbItem.getItemType().equals("TrueFalse")) {
                TrueFalseItem tfUpdatedItem = (TrueFalseItem) updatedItem, tfDbItem = (TrueFalseItem) dbItem;
                updateTrueFalseItem(tfUpdatedItem, tfDbItem);
            }

            session.update(dbItem);
            transaction.commit();

            return true;
        }
        catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return false;
        }
    }

    public void updateMCQItem(MCQItem updatedItem, MCQItem dbItem) {
        if (updatedItem.getOption1() != null)
            dbItem.setOption1(updatedItem.getOption1());

        if (updatedItem.getOption2() != null)
            dbItem.setOption2(updatedItem.getOption2());

        if (updatedItem.getOption3() != null)
            dbItem.setOption3(updatedItem.getOption3());

        if (updatedItem.getOption4() != null)
            dbItem.setOption4(updatedItem.getOption4());

        if (updatedItem.getAnswer() != null)
            dbItem.setAnswer(updatedItem.getAnswer());
    }

    public void updateTrueFalseItem(TrueFalseItem updatedItem, TrueFalseItem dbItem) {
        if (updatedItem.getAnswer() != null)
            dbItem.setAnswer(updatedItem.getAnswer());
    }

}
