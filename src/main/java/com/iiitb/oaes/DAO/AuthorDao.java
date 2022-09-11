package com.iiitb.oaes.DAO;

import com.iiitb.oaes.Bean.Author;
import java.util.List;

public interface AuthorDao {
    boolean registerAuthor(Author author);
    Author loginAuthor(Author author);
    List<Author> getAuthors();

//    Author getAuthorsByID(String authorid);
}
