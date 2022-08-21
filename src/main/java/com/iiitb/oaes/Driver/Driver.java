package com.iiitb.oaes.Driver;

import com.iiitb.oaes.Bean.Authors;
import com.iiitb.oaes.DAO.Implementation.AuthorsImpl;


public class Driver {
    public static void main(String[] args) {
        AuthorsImpl authorsDao = new AuthorsImpl();
        Authors author = new Authors("JJ","JJ123","JJ@123");
        authorsDao.registerAuthor(author);
    }
}
