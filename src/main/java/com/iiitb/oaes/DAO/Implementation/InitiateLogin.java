package com.iiitb.oaes.DAO.Implementation;

import com.iiitb.oaes.Bean.Author;
import com.iiitb.oaes.DAO.HandlerDao;

/*
    COR Phase 1:
    Checks whether Author's login ID and password and non-empty and passes request to next handler in the chain
    Otherwise returns null
*/
public class InitiateLogin implements HandlerDao {
    HandlerDao next;
    @Override
    public Author handle(Author author) {
        if (author.getLoginId().equals("") || author.getPassword().equals(""))
            return null;
        if (next != null)
            return next.handle(author);
        return null;
    }

    public HandlerDao getNext() {
        return next;
    }

    public void setNext(HandlerDao next) {
        this.next = next;
    }
}
