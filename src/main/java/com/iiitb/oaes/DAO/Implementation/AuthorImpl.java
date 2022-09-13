package com.iiitb.oaes.DAO.Implementation;

import com.iiitb.oaes.Bean.Author;
import com.iiitb.oaes.DAO.AuthorDao;
import com.iiitb.oaes.utils.CORUtil;
import com.iiitb.oaes.utils.SessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class AuthorImpl implements AuthorDao {
    @Override
    public Author registerAuthor(Author author) {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(author);
            transaction.commit();
            return author;
        }
        catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Author loginAuthor(Author author) {
        try (Session session = SessionUtil.getSession()) {
            String loginId = author.getLoginId(), password = author.getPassword();

            Query query = session.createQuery("from Author where loginId=:loginId");
            query.setParameter("loginId", loginId);

            for (final Object fetch: query.list())
            {
                Author existingAuthor = (Author) fetch;

                if (existingAuthor.getPassword().equals(password))
                    return existingAuthor;
            }
            return null;
        }
        catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Author> getAuthors() {
        Session session = SessionUtil.getSession();
        List<Author> authors = new ArrayList<>();
        try {
            for (final Object author : session.createQuery("from Author").list()) {
                authors.add((Author) author);
            }
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
        }
        return authors;
    }
}
