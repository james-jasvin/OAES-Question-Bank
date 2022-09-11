package com.iiitb.oaes.DAO.Implementation;


import com.iiitb.oaes.Bean.Author;
import com.iiitb.oaes.DAO.HandlerDao;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public class Sanitize implements HandlerDao {
    HandlerDao next;

    @Override
    public Author handle(Author author) {
        System.out.println("Sanitizing LoginId ...");
        if(next != null){
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
