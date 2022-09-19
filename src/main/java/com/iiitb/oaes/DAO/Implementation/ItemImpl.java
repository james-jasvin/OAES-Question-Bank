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

public class ItemImpl implements ItemDao {
    @Override
    public boolean createItem(Item item, Author author, Integer courseId) {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            CourseDao courseDao = new CourseImpl();
            AuthorDao authorDao = new AuthorImpl();

            Author loggedInAuthor = authorDao.loginAuthor(author);

            if (loggedInAuthor == null)
                return false;

            Course course = courseDao.getCourseById(courseId);

            // Trim the question to prevent empty string questions from being inserted
            item.setQuestion(item.getQuestion().trim());

            // Must similarly trim other attributes later

            item.setAuthor(loggedInAuthor);
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
    public List<Item> getItems(Integer authorId) {
        Session session = SessionUtil.getSession();
        List<Item> items = new ArrayList<>();

        try {
            // Fetch the Author's MCQ Items
            Query query = session.createQuery("from Item where author.authorId=:authorId");
            query.setParameter("authorId", authorId);

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
    public boolean updateItem(Item updatedItem, Integer authorId) {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("from Item where itemId=:itemId and author.authorId=:authorId");
            query.setParameter("itemId", updatedItem.getItemId());
            query.setParameter("authorId", authorId);

            if (query.list().size() == 0)
                return false;

            Item dbItem = (Item) query.list().get(0);

            if (updatedItem.getQuestion() != null)
                dbItem.setQuestion(updatedItem.getQuestion());

            // Else-If Ladder for determining Item Type and calling appropriate update function
            if (updatedItem.getItemType().equals("MCQ")) {
                MCQItem mcqUpdatedItem = (MCQItem) updatedItem, mcqDbItem = (MCQItem) dbItem;
                updateMCQItem(mcqUpdatedItem, mcqDbItem);
            }
            else {
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
