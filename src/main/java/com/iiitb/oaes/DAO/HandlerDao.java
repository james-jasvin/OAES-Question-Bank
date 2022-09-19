package com.iiitb.oaes.DAO;

import com.iiitb.oaes.Bean.Author;

public interface HandlerDao {
    Author handle(Author author);
    void setNext(HandlerDao handler);
}
