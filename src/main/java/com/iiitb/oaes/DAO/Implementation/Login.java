package com.iiitb.oaes.DAO.Implementation;

import com.iiitb.oaes.Bean.Author;
import com.iiitb.oaes.DAO.HandlerDao;
import com.iiitb.oaes.utils.SessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class Login implements HandlerDao {
    HandlerDao next;
    @Override
    public Author handle(String loginId,String password) {
        System.out.println("Logging user in ...");
        if(next != null){
            return next.handle(loginId,password);
        }

        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("from Author where loginId=:loginId");
            query.setParameter("loginId", loginId);

            for (final Object fetch: query.list())
            {
                Author existingAuthor = (Author) fetch;

                if (existingAuthor.getPassword().equals(password))
                    return existingAuthor;
            }
            return null;
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return null;
        }
    }

    public HandlerDao getNext() {
        return next;
    }

    public void setNext(HandlerDao next) {
        this.next = next;
    }
}
