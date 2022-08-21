package com.iiitb.oaes.DAO.Implementation;

import com.iiitb.oaes.Bean.Authors;
import com.iiitb.oaes.DAO.AuthorsDao;
import com.iiitb.oaes.utils.SessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        return null;
    }

    @Override
    public List<Authors> getAuthors() {
        return null;
    }
}
