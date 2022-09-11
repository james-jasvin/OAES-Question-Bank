package com.iiitb.oaes.DAO.Implementation;

import com.iiitb.oaes.Bean.Author;
import com.iiitb.oaes.DAO.HandlerDao;

public class InitiateLogin implements HandlerDao {
    HandlerDao next;
    @Override
    public Author handle(Author author) {
        if(author.getLoginId().equals("") || author.getPassword().equals("")){
            System.out.println("LoginId or Password is missing");
            return null;
        }
        System.out.println("Initiating Login Sequence ...");
        if(next != null){
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
