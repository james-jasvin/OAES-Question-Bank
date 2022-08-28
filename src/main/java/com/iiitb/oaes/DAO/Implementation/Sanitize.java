package com.iiitb.oaes.DAO.Implementation;


import com.iiitb.oaes.Bean.Authors;
import com.iiitb.oaes.DAO.HandlerDao;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public class Sanitize implements HandlerDao {
    HandlerDao next;

    @Override
    public Authors handle(String loginId,String password) {
        System.out.println("Sanitizing LoginId ...");
        if(next != null){
            String safe_loginId = Jsoup.clean(loginId, Safelist.basic());
            return next.handle(safe_loginId,password);
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
