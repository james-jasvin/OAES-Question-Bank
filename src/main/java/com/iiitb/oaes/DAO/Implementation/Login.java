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
    public Author handle(Author author) {
        System.out.println("Logging user in ...");
        if(next != null){
            return next.handle(author);
        }

        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("from Author where loginId=:loginId");
            query.setParameter("loginId", author.getLoginId());

            for (final Object fetch: query.list())
            {
                Author existingAuthor = (Author) fetch;

                if (existingAuthor.getPassword().equals(author.getPassword()))
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
