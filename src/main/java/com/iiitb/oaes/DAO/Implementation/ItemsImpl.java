package com.iiitb.oaes.DAO.Implementation;

import com.iiitb.oaes.Bean.Authors;
import com.iiitb.oaes.Bean.Course;
import com.iiitb.oaes.Bean.Items;
import com.iiitb.oaes.DAO.AuthorsDao;
import com.iiitb.oaes.DAO.CourseDao;
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
    public boolean createItem(Items item, String loginId, String password, Integer courseId) {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            AuthorsDao authorImpl = new AuthorsImpl();
            CourseDao courseImpl = new CourseImpl();

            Authors author = authorImpl.loginAuthor(loginId, password);

            if (author == null)
                return false;

            Course course = courseImpl.getCourseById(courseId);
            item.setAuthor(author);
            item.setCourse(course);

            session.save(item);
            transaction.commit();
            return true;
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public List<Items> getItems(String loginId, String password) {
        Session session = SessionUtil.getSession();
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
            return items;
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean updateItem(Items item, String loginId, String password) {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            AuthorsDao authorImpl = new AuthorsImpl();

            Authors author = authorImpl.loginAuthor(loginId, password);
            if (author == null)
                return false;

            Query query = session.createQuery("from Items where itemId=:itemId and author=:author");
            query.setParameter("itemId", item.getItemId());
            query.setParameter("author", author);

            if (query.list().size() == 0)
                return false;

            Items newObject = (Items) query.list().get(0);

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
            transaction.commit();

            return true;
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return false;
        }
    }
}
