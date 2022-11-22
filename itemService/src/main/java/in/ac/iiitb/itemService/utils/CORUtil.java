package in.ac.iiitb.itemService.utils;

import in.ac.iiitb.itemService.models.Author;
import in.ac.iiitb.itemService.service.*;

/*
    Singleton Class that sets up the Chain of Responsibility for Login Action and provides 
    the initiateCOR() method for starting the chain
*/
public class CORUtil {
    Handler h1, h2, h3;

    // Singleton Instance
    private static CORUtil cor;

    private CORUtil() {
        h1 = new InitiateLogin();
        h2 = new Sanitize();
        h3 = new LoginService();

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
