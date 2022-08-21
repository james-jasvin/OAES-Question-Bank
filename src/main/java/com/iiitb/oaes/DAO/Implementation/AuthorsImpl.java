package com.iiitb.oaes.DAO.Implementation;

import com.iiitb.oaes.Bean.Authors;
import com.iiitb.oaes.Bean.Items;
import com.iiitb.oaes.DAO.AuthorsDao;
import com.iiitb.oaes.utils.SessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class AuthorsImpl implements AuthorsDao {
    @Override
    public boolean registerAuthor(Authors author) {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(author);
            transaction.commit();
            return true;
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public Authors loginAuthor(String loginId, String password) {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("from Authors where loginId=:loginId");
            query.setParameter("loginId", loginId);

            for (final Object fetch: query.list())
            {
                Authors existingAuthor = (Authors) fetch;

                if (existingAuthor.getPassword().equals(password))
                    return existingAuthor;
            }
            return null;
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Authors> getAuthors() {
        Session session = SessionUtil.getSession();
        List<Authors> authors = new ArrayList<>();
        try {
            for (final Object author : session.createQuery("from Authors").list()) {
                authors.add((Authors) author);
            }
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
        }
        return authors;
    }
}
