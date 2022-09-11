package com.iiitb.oaes.Service;

import com.iiitb.oaes.Bean.Author;
import com.iiitb.oaes.DAO.AuthorDao;
import com.iiitb.oaes.DAO.Implementation.AuthorImpl;

public class AuthorService {
    AuthorDao authorDAO = new AuthorImpl();

    public Author login(Author author){
        Author loggedInAuthor = authorDAO.loginAuthor(author);

        // If no login happens, then return null
        if (loggedInAuthor == null)
            return null;

        // Setting billList to null to avoid cyclic dependency issues
        loggedInAuthor.setItems(null);

        return loggedInAuthor;
    }
}