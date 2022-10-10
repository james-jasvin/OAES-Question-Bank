package com.iiitb.oaes.DAO.Implementation;

import com.iiitb.oaes.Bean.*;
import com.iiitb.oaes.DAO.AuthorDao;
import com.iiitb.oaes.DAO.CourseDao;
import com.iiitb.oaes.DAO.ItemDao;
import com.iiitb.oaes.utils.CORUtil;
import com.iiitb.oaes.utils.SessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ItemImpl implements ItemDao {
    CORUtil COR = CORUtil.getCOR();

    /*
        Create a new Item with the specified Author and Course (indicated by CourseId) in the Database
        - Initiate the Login COR to ensure that Author credentials are valid
        - Fetch Course DB object that's specified by the Course Id in the input
        - Set Author and Course for the created Item and insert the Item into the Database
    */
    @Override
    public boolean createItem(Item item, Author author, Integer courseId) {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            CourseDao courseDao = new CourseImpl();

            Author loggedInAuthor = COR.initiateCOR(author);
            if (loggedInAuthor == null)
                return false;

            Course course = courseDao.getCourseById(courseId);
            // If invalid courseId specified which is why course is null, item creation fails
            if (course == null)
                return false;

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

    /*
        Get all Items of the specified Author via AuthorId in the input data
    */
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

    /*
        Update an existing Item with the details specified in the input
        If a field is to be left unchanged, that field will be set as null in the input

        - Fetch the database Item
        - Set all the fields in the DB Item from the updatedItem (input data) if the corresponding field is non-null
        - For this you need separate utility methods for handling each type of Item, MCQ/TrueFalse/etc.
    */
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

            // Question is common attribute to all Items, so its handled here
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

    // Only update those fields in dbItem which are non-NULL in updatedItem
    // Same thing has to be repeated for all Item Types

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
