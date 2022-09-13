package com.iiitb.oaes.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iiitb.oaes.Bean.Author;
import com.iiitb.oaes.utils.CORUtil;

public class AuthorService {
    CORUtil COR = CORUtil.getCOR();

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