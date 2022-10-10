package com.iiitb.oaes.DAO.Implementation;


import com.iiitb.oaes.Bean.Author;
import com.iiitb.oaes.DAO.HandlerDao;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

/*
    COR Phase 2:
    Cleans the user login ID of SQL injection characters via Jsoup and calls the next handler in the chain
*/
public class Sanitize implements HandlerDao {
    HandlerDao next;

    @Override
    public Author handle(Author author) {
        if (next != null) {
            author.setLoginId(Jsoup.clean(author.getLoginId(), Safelist.basic()));
            return next.handle(author);
        }
        return null;
    }

    public HandlerDao getNext() {
        return next;
    }

    public void setNext(HandlerDao next) {
        this.next = next;
    }
}
