package com.iiitb.oaes.utils;

import com.iiitb.oaes.Bean.Author;
import com.iiitb.oaes.DAO.Implementation.InitiateLogin;
import com.iiitb.oaes.DAO.Implementation.Login;
import com.iiitb.oaes.DAO.Implementation.Sanitize;

public class CORUtil {
    InitiateLogin h1;
    Sanitize h2;
    Login h3;

    // Singleton Instance
    private static CORUtil cor;

    private CORUtil() {
        h1 = new InitiateLogin();
        h2 = new Sanitize();
        h3 = new Login();

        h1.setNext(h2);
        h2.setNext(h3);
    }

    public static CORUtil getCOR() {
        if (cor == null)
            cor = new CORUtil();
        return cor;
    }

    public Author initiateCOR(Author author) {
        return h1.handle(author);
    }
}
