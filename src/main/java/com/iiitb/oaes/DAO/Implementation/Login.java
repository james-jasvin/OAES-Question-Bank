package com.iiitb.oaes.DAO.Implementation;

import com.iiitb.oaes.Bean.Author;
import com.iiitb.oaes.DAO.AuthorDao;
import com.iiitb.oaes.DAO.HandlerDao;
import com.iiitb.oaes.utils.SessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class Login implements HandlerDao {
    AuthorDao authorDao;
    HandlerDao next;

    public Login() {
        this.authorDao = new AuthorImpl();
    }

    @Override
    public Author handle(Author author) {
        return authorDao.loginAuthor(author);
    }

    public HandlerDao getNext() {
        return next;
    }

    public void setNext(HandlerDao next) {
        this.next = next;
    }
}
