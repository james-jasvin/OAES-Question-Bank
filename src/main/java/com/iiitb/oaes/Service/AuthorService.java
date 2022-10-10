package com.iiitb.oaes.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iiitb.oaes.Bean.Author;
import com.iiitb.oaes.utils.CORUtil;

public class AuthorService {
    // Obtain Object of CORUtil class in order to initiate Chain of Responsibility on demand
    CORUtil COR = CORUtil.getCOR();

    /*
        - Takes in the JSON which contains author login data
        - Converts it to an object of class Author
        - Initiates Login Chain of Responsibility via the initiateCOR() method
        - If COR returns null, it means login failed, so return null
        - Otherwise return the logged in Author
    */
    public Author login(String authorJSON) throws JsonProcessingException {
        Author author = new ObjectMapper().readValue(authorJSON, Author.class);

        Author loggedInAuthor = COR.initiateCOR(author);

        // If no login happens, then return null
        if (loggedInAuthor == null)
            return null;

        // Setting items to null to avoid cyclic dependency issues
        loggedInAuthor.setItems(null);

        return loggedInAuthor;
    }
}